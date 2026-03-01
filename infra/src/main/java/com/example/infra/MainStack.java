package com.example.infra;

import java.util.List;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.apigateway.EndpointType;
import software.amazon.awscdk.services.apigateway.Integration;
import software.amazon.awscdk.services.apigateway.IntegrationType;
import software.amazon.awscdk.services.apigateway.MethodOptions;
import software.amazon.awscdk.services.apigateway.MockIntegration;
import software.amazon.awscdk.services.apigateway.PassthroughBehavior;
import software.amazon.awscdk.services.apigateway.RestApi;
import software.amazon.awscdk.services.apigateway.StageOptions;
import software.amazon.awscdk.services.dynamodb.Attribute;
import software.amazon.awscdk.services.dynamodb.AttributeType;
import software.amazon.awscdk.services.dynamodb.BillingMode;
import software.amazon.awscdk.services.dynamodb.Table;
import software.amazon.awscdk.services.ec2.IpAddresses;
import software.amazon.awscdk.services.ec2.SubnetConfiguration;
import software.amazon.awscdk.services.ec2.SubnetType;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.s3.BlockPublicAccess;
import software.amazon.awscdk.services.s3.Bucket;
import software.amazon.awscdk.services.s3.BucketEncryption;
import software.amazon.awscdk.services.sqs.Queue;
import software.constructs.Construct;

/** Main CDK stack with AWS resources including VPC, API Gateways, and data stores. */
public class MainStack extends Stack {

  private final Vpc vpc;
  private final Bucket bucket;
  private final Queue queue;
  private final Table table;
  private final RestApi restApi;
  private final RestApi soapApi;

  public MainStack(final Construct scope, final String id, final StackProps props) {
    super(scope, id, props);

    this.vpc = createVpc();
    this.bucket = createBucket();
    this.queue = createQueue();
    this.table = createTable();
    this.restApi = createRestApi();
    this.soapApi = createSoapApi();
  }

  private Vpc createVpc() {
    return Vpc.Builder.create(this, "MainVpc")
        .ipAddresses(IpAddresses.cidr("10.0.0.0/16"))
        .maxAzs(2)
        .subnetConfiguration(
            List.of(
                SubnetConfiguration.builder()
                    .name("Public")
                    .subnetType(SubnetType.PUBLIC)
                    .cidrMask(24)
                    .build(),
                SubnetConfiguration.builder()
                    .name("Private")
                    .subnetType(SubnetType.PRIVATE_WITH_EGRESS)
                    .cidrMask(24)
                    .build(),
                SubnetConfiguration.builder()
                    .name("Isolated")
                    .subnetType(SubnetType.PRIVATE_ISOLATED)
                    .cidrMask(24)
                    .build()))
        .build();
  }

  private Bucket createBucket() {
    return Bucket.Builder.create(this, "SampleBucket")
        .encryption(BucketEncryption.S3_MANAGED)
        .blockPublicAccess(BlockPublicAccess.BLOCK_ALL)
        .removalPolicy(RemovalPolicy.DESTROY)
        .autoDeleteObjects(true)
        .build();
  }

  private Queue createQueue() {
    return Queue.Builder.create(this, "SampleQueue").build();
  }

  private Table createTable() {
    return Table.Builder.create(this, "SampleTable")
        .partitionKey(Attribute.builder().name("id").type(AttributeType.STRING).build())
        .billingMode(BillingMode.PAY_PER_REQUEST)
        .removalPolicy(RemovalPolicy.DESTROY)
        .build();
  }

  private RestApi createRestApi() {
    RestApi api =
        RestApi.Builder.create(this, "RestServiceApi")
            .restApiName("REST Service API")
            .description("API Gateway for REST service")
            .endpointTypes(List.of(EndpointType.REGIONAL))
            .deployOptions(StageOptions.builder().stageName("prod").build())
            .build();

    // Health endpoint
    var healthResource = api.getRoot().addResource("health");
    healthResource.addMethod(
        "GET",
        MockIntegration.Builder.create()
            .integrationResponses(
                List.of(
                    software.amazon.awscdk.services.apigateway.IntegrationResponse.builder()
                        .statusCode("200")
                        .responseTemplates(
                            java.util.Map.of("application/json", "{\"status\": \"UP\"}"))
                        .build()))
            .passthroughBehavior(PassthroughBehavior.WHEN_NO_MATCH)
            .requestTemplates(java.util.Map.of("application/json", "{\"statusCode\": 200}"))
            .build(),
        MethodOptions.builder()
            .methodResponses(
                List.of(
                    software.amazon.awscdk.services.apigateway.MethodResponse.builder()
                        .statusCode("200")
                        .build()))
            .build());

    // Samples endpoint
    var samplesResource = api.getRoot().addResource("samples");
    var sampleIdResource = samplesResource.addResource("{id}");
    sampleIdResource.addMethod(
        "GET",
        createMockIntegration(),
        MethodOptions.builder()
            .methodResponses(
                List.of(
                    software.amazon.awscdk.services.apigateway.MethodResponse.builder()
                        .statusCode("200")
                        .build()))
            .build());

    return api;
  }

  private RestApi createSoapApi() {
    RestApi api =
        RestApi.Builder.create(this, "SoapServiceApi")
            .restApiName("SOAP Service API")
            .description("API Gateway for SOAP service")
            .endpointTypes(List.of(EndpointType.REGIONAL))
            .deployOptions(StageOptions.builder().stageName("prod").build())
            .build();

    // SOAP endpoint (ws path)
    var wsResource = api.getRoot().addResource("ws");
    wsResource.addMethod(
        "POST",
        createMockIntegration(),
        MethodOptions.builder()
            .methodResponses(
                List.of(
                    software.amazon.awscdk.services.apigateway.MethodResponse.builder()
                        .statusCode("200")
                        .build()))
            .build());

    // WSDL endpoint
    var wsdlResource = wsResource.addResource("sample.wsdl");
    wsdlResource.addMethod(
        "GET",
        createMockIntegration(),
        MethodOptions.builder()
            .methodResponses(
                List.of(
                    software.amazon.awscdk.services.apigateway.MethodResponse.builder()
                        .statusCode("200")
                        .build()))
            .build());

    return api;
  }

  private Integration createMockIntegration() {
    return Integration.Builder.create()
        .type(IntegrationType.MOCK)
        .integrationHttpMethod("POST")
        .options(
            software.amazon.awscdk.services.apigateway.IntegrationOptions.builder()
                .integrationResponses(
                    List.of(
                        software.amazon.awscdk.services.apigateway.IntegrationResponse.builder()
                            .statusCode("200")
                            .build()))
                .passthroughBehavior(PassthroughBehavior.WHEN_NO_MATCH)
                .requestTemplates(java.util.Map.of("application/json", "{\"statusCode\": 200}"))
                .build())
        .build();
  }

  public Vpc getVpc() {
    return vpc;
  }

  public Bucket getBucket() {
    return bucket;
  }

  public Queue getQueue() {
    return queue;
  }

  public Table getTable() {
    return table;
  }

  public RestApi getRestApi() {
    return restApi;
  }

  public RestApi getSoapApi() {
    return soapApi;
  }
}
