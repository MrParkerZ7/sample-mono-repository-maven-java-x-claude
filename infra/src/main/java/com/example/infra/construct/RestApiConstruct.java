package com.example.infra.construct;

import java.util.List;
import java.util.Map;
import software.amazon.awscdk.services.apigateway.EndpointType;
import software.amazon.awscdk.services.apigateway.IntegrationResponse;
import software.amazon.awscdk.services.apigateway.MethodOptions;
import software.amazon.awscdk.services.apigateway.MethodResponse;
import software.amazon.awscdk.services.apigateway.MockIntegration;
import software.amazon.awscdk.services.apigateway.PassthroughBehavior;
import software.amazon.awscdk.services.apigateway.RestApi;
import software.amazon.awscdk.services.apigateway.StageOptions;
import software.constructs.Construct;

/** Construct for REST API Gateway. */
public class RestApiConstruct extends Construct {

  private final RestApi restApi;

  public RestApiConstruct(final Construct scope, final String id) {
    super(scope, id);

    this.restApi =
        RestApi.Builder.create(this, "RestServiceApi")
            .restApiName("REST Service API")
            .description("API Gateway for REST service")
            .endpointTypes(List.of(EndpointType.REGIONAL))
            .deployOptions(StageOptions.builder().stageName("prod").build())
            .build();

    createHealthEndpoint();
    createSamplesEndpoint();
  }

  private void createHealthEndpoint() {
    var healthResource = restApi.getRoot().addResource("health");
    healthResource.addMethod(
        "GET",
        MockIntegration.Builder.create()
            .integrationResponses(
                List.of(
                    IntegrationResponse.builder()
                        .statusCode("200")
                        .responseTemplates(Map.of("application/json", "{\"status\": \"UP\"}"))
                        .build()))
            .passthroughBehavior(PassthroughBehavior.WHEN_NO_MATCH)
            .requestTemplates(Map.of("application/json", "{\"statusCode\": 200}"))
            .build(),
        MethodOptions.builder()
            .methodResponses(List.of(MethodResponse.builder().statusCode("200").build()))
            .build());
  }

  private void createSamplesEndpoint() {
    var samplesResource = restApi.getRoot().addResource("samples");
    var sampleIdResource = samplesResource.addResource("{id}");
    sampleIdResource.addMethod(
        "GET",
        MockIntegration.Builder.create()
            .integrationResponses(List.of(IntegrationResponse.builder().statusCode("200").build()))
            .passthroughBehavior(PassthroughBehavior.WHEN_NO_MATCH)
            .requestTemplates(Map.of("application/json", "{\"statusCode\": 200}"))
            .build(),
        MethodOptions.builder()
            .methodResponses(List.of(MethodResponse.builder().statusCode("200").build()))
            .build());
  }

  public RestApi getRestApi() {
    return restApi;
  }
}
