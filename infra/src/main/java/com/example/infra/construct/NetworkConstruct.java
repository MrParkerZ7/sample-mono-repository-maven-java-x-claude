package com.example.infra.construct;

import java.util.List;
import software.amazon.awscdk.services.ec2.IpAddresses;
import software.amazon.awscdk.services.ec2.SubnetConfiguration;
import software.amazon.awscdk.services.ec2.SubnetType;
import software.amazon.awscdk.services.ec2.Vpc;
import software.constructs.Construct;

/** Construct for VPC and networking resources. */
public class NetworkConstruct extends Construct {

  private final Vpc vpc;

  public NetworkConstruct(final Construct scope, final String id) {
    super(scope, id);

    this.vpc =
        Vpc.Builder.create(this, "MainVpc")
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

  public Vpc getVpc() {
    return vpc;
  }
}
