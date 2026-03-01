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

/** Construct for SOAP API Gateway. */
public class SoapApiConstruct extends Construct {

  private final RestApi soapApi;

  public SoapApiConstruct(final Construct scope, final String id) {
    super(scope, id);

    this.soapApi =
        RestApi.Builder.create(this, "SoapServiceApi")
            .restApiName("SOAP Service API")
            .description("API Gateway for SOAP service")
            .endpointTypes(List.of(EndpointType.REGIONAL))
            .deployOptions(StageOptions.builder().stageName("prod").build())
            .build();

    var wsResource = createWsEndpoint();
    createWsdlEndpoint(wsResource);
  }

  private software.amazon.awscdk.services.apigateway.Resource createWsEndpoint() {
    var wsResource = soapApi.getRoot().addResource("ws");
    wsResource.addMethod(
        "POST",
        MockIntegration.Builder.create()
            .integrationResponses(List.of(IntegrationResponse.builder().statusCode("200").build()))
            .passthroughBehavior(PassthroughBehavior.WHEN_NO_MATCH)
            .requestTemplates(Map.of("application/xml", "{\"statusCode\": 200}"))
            .build(),
        MethodOptions.builder()
            .methodResponses(List.of(MethodResponse.builder().statusCode("200").build()))
            .build());
    return wsResource;
  }

  private void createWsdlEndpoint(software.amazon.awscdk.services.apigateway.Resource wsResource) {
    var wsdlResource = wsResource.addResource("sample.wsdl");
    wsdlResource.addMethod(
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

  public RestApi getSoapApi() {
    return soapApi;
  }
}
