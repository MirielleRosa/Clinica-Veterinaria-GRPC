package javafx.agendamento;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.59.1)",
    comments = "Source: agenda.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AgendaServiceGrpc {

  private AgendaServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "javafx.agendamento.AgendaService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<javafx.agendamento.GetAgendamentosRequest,
      javafx.agendamento.GetAgendamentosResponse> getGetAgendamentosMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAgendamentos",
      requestType = javafx.agendamento.GetAgendamentosRequest.class,
      responseType = javafx.agendamento.GetAgendamentosResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<javafx.agendamento.GetAgendamentosRequest,
      javafx.agendamento.GetAgendamentosResponse> getGetAgendamentosMethod() {
    io.grpc.MethodDescriptor<javafx.agendamento.GetAgendamentosRequest, javafx.agendamento.GetAgendamentosResponse> getGetAgendamentosMethod;
    if ((getGetAgendamentosMethod = AgendaServiceGrpc.getGetAgendamentosMethod) == null) {
      synchronized (AgendaServiceGrpc.class) {
        if ((getGetAgendamentosMethod = AgendaServiceGrpc.getGetAgendamentosMethod) == null) {
          AgendaServiceGrpc.getGetAgendamentosMethod = getGetAgendamentosMethod =
              io.grpc.MethodDescriptor.<javafx.agendamento.GetAgendamentosRequest, javafx.agendamento.GetAgendamentosResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAgendamentos"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  javafx.agendamento.GetAgendamentosRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  javafx.agendamento.GetAgendamentosResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AgendaServiceMethodDescriptorSupplier("GetAgendamentos"))
              .build();
        }
      }
    }
    return getGetAgendamentosMethod;
  }

  private static volatile io.grpc.MethodDescriptor<javafx.agendamento.AtualizarStatusAgendamentoRequest,
      javafx.agendamento.AtualizarStatusAgendamentoResponse> getAtualizarStatusAgendamentoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AtualizarStatusAgendamento",
      requestType = javafx.agendamento.AtualizarStatusAgendamentoRequest.class,
      responseType = javafx.agendamento.AtualizarStatusAgendamentoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<javafx.agendamento.AtualizarStatusAgendamentoRequest,
      javafx.agendamento.AtualizarStatusAgendamentoResponse> getAtualizarStatusAgendamentoMethod() {
    io.grpc.MethodDescriptor<javafx.agendamento.AtualizarStatusAgendamentoRequest, javafx.agendamento.AtualizarStatusAgendamentoResponse> getAtualizarStatusAgendamentoMethod;
    if ((getAtualizarStatusAgendamentoMethod = AgendaServiceGrpc.getAtualizarStatusAgendamentoMethod) == null) {
      synchronized (AgendaServiceGrpc.class) {
        if ((getAtualizarStatusAgendamentoMethod = AgendaServiceGrpc.getAtualizarStatusAgendamentoMethod) == null) {
          AgendaServiceGrpc.getAtualizarStatusAgendamentoMethod = getAtualizarStatusAgendamentoMethod =
              io.grpc.MethodDescriptor.<javafx.agendamento.AtualizarStatusAgendamentoRequest, javafx.agendamento.AtualizarStatusAgendamentoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AtualizarStatusAgendamento"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  javafx.agendamento.AtualizarStatusAgendamentoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  javafx.agendamento.AtualizarStatusAgendamentoResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AgendaServiceMethodDescriptorSupplier("AtualizarStatusAgendamento"))
              .build();
        }
      }
    }
    return getAtualizarStatusAgendamentoMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AgendaServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AgendaServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AgendaServiceStub>() {
        @java.lang.Override
        public AgendaServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AgendaServiceStub(channel, callOptions);
        }
      };
    return AgendaServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AgendaServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AgendaServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AgendaServiceBlockingStub>() {
        @java.lang.Override
        public AgendaServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AgendaServiceBlockingStub(channel, callOptions);
        }
      };
    return AgendaServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AgendaServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AgendaServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AgendaServiceFutureStub>() {
        @java.lang.Override
        public AgendaServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AgendaServiceFutureStub(channel, callOptions);
        }
      };
    return AgendaServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getAgendamentos(javafx.agendamento.GetAgendamentosRequest request,
        io.grpc.stub.StreamObserver<javafx.agendamento.GetAgendamentosResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAgendamentosMethod(), responseObserver);
    }

    /**
     */
    default void atualizarStatusAgendamento(javafx.agendamento.AtualizarStatusAgendamentoRequest request,
        io.grpc.stub.StreamObserver<javafx.agendamento.AtualizarStatusAgendamentoResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAtualizarStatusAgendamentoMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service AgendaService.
   */
  public static abstract class AgendaServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return AgendaServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service AgendaService.
   */
  public static final class AgendaServiceStub
      extends io.grpc.stub.AbstractAsyncStub<AgendaServiceStub> {
    private AgendaServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AgendaServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AgendaServiceStub(channel, callOptions);
    }

    /**
     */
    public void getAgendamentos(javafx.agendamento.GetAgendamentosRequest request,
        io.grpc.stub.StreamObserver<javafx.agendamento.GetAgendamentosResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAgendamentosMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void atualizarStatusAgendamento(javafx.agendamento.AtualizarStatusAgendamentoRequest request,
        io.grpc.stub.StreamObserver<javafx.agendamento.AtualizarStatusAgendamentoResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAtualizarStatusAgendamentoMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service AgendaService.
   */
  public static final class AgendaServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<AgendaServiceBlockingStub> {
    private AgendaServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AgendaServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AgendaServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public javafx.agendamento.GetAgendamentosResponse getAgendamentos(javafx.agendamento.GetAgendamentosRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAgendamentosMethod(), getCallOptions(), request);
    }

    /**
     */
    public javafx.agendamento.AtualizarStatusAgendamentoResponse atualizarStatusAgendamento(javafx.agendamento.AtualizarStatusAgendamentoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAtualizarStatusAgendamentoMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service AgendaService.
   */
  public static final class AgendaServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<AgendaServiceFutureStub> {
    private AgendaServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AgendaServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AgendaServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<javafx.agendamento.GetAgendamentosResponse> getAgendamentos(
        javafx.agendamento.GetAgendamentosRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAgendamentosMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<javafx.agendamento.AtualizarStatusAgendamentoResponse> atualizarStatusAgendamento(
        javafx.agendamento.AtualizarStatusAgendamentoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAtualizarStatusAgendamentoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_AGENDAMENTOS = 0;
  private static final int METHODID_ATUALIZAR_STATUS_AGENDAMENTO = 1;

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
        case METHODID_GET_AGENDAMENTOS:
          serviceImpl.getAgendamentos((javafx.agendamento.GetAgendamentosRequest) request,
              (io.grpc.stub.StreamObserver<javafx.agendamento.GetAgendamentosResponse>) responseObserver);
          break;
        case METHODID_ATUALIZAR_STATUS_AGENDAMENTO:
          serviceImpl.atualizarStatusAgendamento((javafx.agendamento.AtualizarStatusAgendamentoRequest) request,
              (io.grpc.stub.StreamObserver<javafx.agendamento.AtualizarStatusAgendamentoResponse>) responseObserver);
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
          getGetAgendamentosMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              javafx.agendamento.GetAgendamentosRequest,
              javafx.agendamento.GetAgendamentosResponse>(
                service, METHODID_GET_AGENDAMENTOS)))
        .addMethod(
          getAtualizarStatusAgendamentoMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              javafx.agendamento.AtualizarStatusAgendamentoRequest,
              javafx.agendamento.AtualizarStatusAgendamentoResponse>(
                service, METHODID_ATUALIZAR_STATUS_AGENDAMENTO)))
        .build();
  }

  private static abstract class AgendaServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AgendaServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return javafx.agendamento.AgendaProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AgendaService");
    }
  }

  private static final class AgendaServiceFileDescriptorSupplier
      extends AgendaServiceBaseDescriptorSupplier {
    AgendaServiceFileDescriptorSupplier() {}
  }

  private static final class AgendaServiceMethodDescriptorSupplier
      extends AgendaServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    AgendaServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (AgendaServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AgendaServiceFileDescriptorSupplier())
              .addMethod(getGetAgendamentosMethod())
              .addMethod(getAtualizarStatusAgendamentoMethod())
              .build();
        }
      }
    }
    return result;
  }
}
