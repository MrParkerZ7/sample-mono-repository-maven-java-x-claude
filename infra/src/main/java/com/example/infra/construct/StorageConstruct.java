package com.example.infra.construct;

import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.services.dynamodb.Attribute;
import software.amazon.awscdk.services.dynamodb.AttributeType;
import software.amazon.awscdk.services.dynamodb.BillingMode;
import software.amazon.awscdk.services.dynamodb.Table;
import software.amazon.awscdk.services.s3.BlockPublicAccess;
import software.amazon.awscdk.services.s3.Bucket;
import software.amazon.awscdk.services.s3.BucketEncryption;
import software.amazon.awscdk.services.sqs.Queue;
import software.constructs.Construct;

/** Construct for storage resources (S3, SQS, DynamoDB). */
public class StorageConstruct extends Construct {

  private final Bucket bucket;
  private final Queue queue;
  private final Table table;

  public StorageConstruct(final Construct scope, final String id) {
    super(scope, id);

    this.bucket =
        Bucket.Builder.create(this, "SampleBucket")
            .encryption(BucketEncryption.S3_MANAGED)
            .blockPublicAccess(BlockPublicAccess.BLOCK_ALL)
            .removalPolicy(RemovalPolicy.DESTROY)
            .autoDeleteObjects(true)
            .build();

    this.queue = Queue.Builder.create(this, "SampleQueue").build();

    this.table =
        Table.Builder.create(this, "SampleTable")
            .partitionKey(Attribute.builder().name("id").type(AttributeType.STRING).build())
            .billingMode(BillingMode.PAY_PER_REQUEST)
            .removalPolicy(RemovalPolicy.DESTROY)
            .build();
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
}
