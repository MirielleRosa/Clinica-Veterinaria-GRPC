package grpcjavafx;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.59.1)",
    comments = "Source: equipe.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class EquipeServiceGrpc {

  private EquipeServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "EquipeService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpcjavafx.EquipeRequest,
      grpcjavafx.EquipeResponse> getGetEquipeInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getEquipeInfo",
      requestType = grpcjavafx.EquipeRequest.class,
      responseType = grpcjavafx.EquipeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpcjavafx.EquipeRequest,
      grpcjavafx.EquipeResponse> getGetEquipeInfoMethod() {
    io.grpc.MethodDescriptor<grpcjavafx.EquipeRequest, grpcjavafx.EquipeResponse> getGetEquipeInfoMethod;
    if ((getGetEquipeInfoMethod = EquipeServiceGrpc.getGetEquipeInfoMethod) == null) {
      synchronized (EquipeServiceGrpc.class) {
        if ((getGetEquipeInfoMethod = EquipeServiceGrpc.getGetEquipeInfoMethod) == null) {
          EquipeServiceGrpc.getGetEquipeInfoMethod = getGetEquipeInfoMethod =
              io.grpc.MethodDescriptor.<grpcjavafx.EquipeRequest, grpcjavafx.EquipeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getEquipeInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpcjavafx.EquipeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpcjavafx.EquipeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EquipeServiceMethodDescriptorSupplier("getEquipeInfo"))
              .build();
        }
      }
    }
    return getGetEquipeInfoMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static EquipeServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EquipeServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EquipeServiceStub>() {
        @java.lang.Override
        public EquipeServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EquipeServiceStub(channel, callOptions);
        }
      };
    return EquipeServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EquipeServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EquipeServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EquipeServiceBlockingStub>() {
        @java.lang.Override
        public EquipeServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EquipeServiceBlockingStub(channel, callOptions);
        }
      };
    return EquipeServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static EquipeServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EquipeServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EquipeServiceFutureStub>() {
        @java.lang.Override
        public EquipeServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EquipeServiceFutureStub(channel, callOptions);
        }
      };
    return EquipeServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getEquipeInfo(grpcjavafx.EquipeRequest request,
        io.grpc.stub.StreamObserver<grpcjavafx.EquipeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetEquipeInfoMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service EquipeService.
   */
  public static abstract class EquipeServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return EquipeServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service EquipeService.
   */
  public static final class EquipeServiceStub
      extends io.grpc.stub.AbstractAsyncStub<EquipeServiceStub> {
    private EquipeServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EquipeServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EquipeServiceStub(channel, callOptions);
    }

    /**
     */
    public void getEquipeInfo(grpcjavafx.EquipeRequest request,
        io.grpc.stub.StreamObserver<grpcjavafx.EquipeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetEquipeInfoMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service EquipeService.
   */
  public static final class EquipeServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<EquipeServiceBlockingStub> {
    private EquipeServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EquipeServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EquipeServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public grpcjavafx.EquipeResponse getEquipeInfo(grpcjavafx.EquipeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetEquipeInfoMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service EquipeService.
   */
  public static final class EquipeServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<EquipeServiceFutureStub> {
    private EquipeServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EquipeServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EquipeServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpcjavafx.EquipeResponse> getEquipeInfo(
        grpcjavafx.EquipeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetEquipeInfoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_EQUIPE_INFO = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_EQUIPE_INFO:
          serviceImpl.getEquipeInfo((grpcjavafx.EquipeRequest) request,
              (io.grpc.stub.StreamObserver<grpcjavafx.EquipeResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGetEquipeInfoMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpcjavafx.EquipeRequest,
              grpcjavafx.EquipeResponse>(
                service, METHODID_GET_EQUIPE_INFO)))
        .build();
  }

  private static abstract class EquipeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    EquipeServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpcjavafx.EquipeProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("EquipeService");
    }
  }

  private static final class EquipeServiceFileDescriptorSupplier
      extends EquipeServiceBaseDescriptorSupplier {
    EquipeServiceFileDescriptorSupplier() {}
  }

  private static final class EquipeServiceMethodDescriptorSupplier
      extends EquipeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    EquipeServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (EquipeServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new EquipeServiceFileDescriptorSupplier())
              .addMethod(getGetEquipeInfoMethod())
              .build();
        }
      }
    }
    return result;
  }
}
