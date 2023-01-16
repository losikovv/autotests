package product_hub_front_data;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * slack:
 *    #product-hub
 * base:
 *     base-product-hub.paas-content-product-hub:3009
 * local:
 *     base-product-hub.paas-content-product-hub.svc.cluster.local:3009
 * swagger prod:
 *     https://paas-content-product-hub.sbmt.io/api
 * swagger stg:
 *     https://paas-content-product-hub.gw-stage.sbmt.io/api
 * public prod grpc uri:
 *    paas-content-product-hub.sbmt.io:443
 * public stg grpc uri:
 *    paas-content-product-hub.gw-stage.sbmt.io:443
 * description:
 *    Фронтовый сервис product-hub возвращающий описание товаров (product+attribute),
 *    товарное вредложение (offer), цену (price) и остатки (stock).
 *    Полный набор атрибутов и флагов для всех сущностей описан здесь https://wiki.sbmt.io/display/CP/Product+Hub
 *    Предназначет для real-time нагрузки.
 *    Основные параметры запроса:
 *       sku - сбермартовский идентификатор описания товара
 *       store_id - сбермартовский идентификатор магазина
 *       tenant_id - сбермартовский идентификатор площадки
 *       retailer_id - сбермартовский идентификатор ритейлера
 *       retailer_sku - ритейлерский идентификатор товарного предложения
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: content/product_hub/product-hub-front-data.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ProductHubFrontDataGrpc {

  private ProductHubFrontDataGrpc() {}

  public static final String SERVICE_NAME = "product_hub_front_data.ProductHubFrontData";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse> getGetProductsBySKUMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProductsBySKU",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse> getGetProductsBySKUMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse> getGetProductsBySKUMethod;
    if ((getGetProductsBySKUMethod = ProductHubFrontDataGrpc.getGetProductsBySKUMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetProductsBySKUMethod = ProductHubFrontDataGrpc.getGetProductsBySKUMethod) == null) {
          ProductHubFrontDataGrpc.getGetProductsBySKUMethod = getGetProductsBySKUMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProductsBySKU"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetProductsBySKU"))
              .build();
        }
      }
    }
    return getGetProductsBySKUMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse> getGetProductsWithOfferBySKUMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProductsWithOfferBySKU",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse> getGetProductsWithOfferBySKUMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse> getGetProductsWithOfferBySKUMethod;
    if ((getGetProductsWithOfferBySKUMethod = ProductHubFrontDataGrpc.getGetProductsWithOfferBySKUMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetProductsWithOfferBySKUMethod = ProductHubFrontDataGrpc.getGetProductsWithOfferBySKUMethod) == null) {
          ProductHubFrontDataGrpc.getGetProductsWithOfferBySKUMethod = getGetProductsWithOfferBySKUMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProductsWithOfferBySKU"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetProductsWithOfferBySKU"))
              .build();
        }
      }
    }
    return getGetProductsWithOfferBySKUMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse> getGetProductsWithOfferByOfferIDsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProductsWithOfferByOfferIDs",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse> getGetProductsWithOfferByOfferIDsMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse> getGetProductsWithOfferByOfferIDsMethod;
    if ((getGetProductsWithOfferByOfferIDsMethod = ProductHubFrontDataGrpc.getGetProductsWithOfferByOfferIDsMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetProductsWithOfferByOfferIDsMethod = ProductHubFrontDataGrpc.getGetProductsWithOfferByOfferIDsMethod) == null) {
          ProductHubFrontDataGrpc.getGetProductsWithOfferByOfferIDsMethod = getGetProductsWithOfferByOfferIDsMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProductsWithOfferByOfferIDs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetProductsWithOfferByOfferIDs"))
              .build();
        }
      }
    }
    return getGetProductsWithOfferByOfferIDsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse> getGetProductsByEANMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProductsByEAN",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse> getGetProductsByEANMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse> getGetProductsByEANMethod;
    if ((getGetProductsByEANMethod = ProductHubFrontDataGrpc.getGetProductsByEANMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetProductsByEANMethod = ProductHubFrontDataGrpc.getGetProductsByEANMethod) == null) {
          ProductHubFrontDataGrpc.getGetProductsByEANMethod = getGetProductsByEANMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProductsByEAN"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetProductsByEAN"))
              .build();
        }
      }
    }
    return getGetProductsByEANMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse> getGetProductsWithOfferByEANMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProductsWithOfferByEAN",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse> getGetProductsWithOfferByEANMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse> getGetProductsWithOfferByEANMethod;
    if ((getGetProductsWithOfferByEANMethod = ProductHubFrontDataGrpc.getGetProductsWithOfferByEANMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetProductsWithOfferByEANMethod = ProductHubFrontDataGrpc.getGetProductsWithOfferByEANMethod) == null) {
          ProductHubFrontDataGrpc.getGetProductsWithOfferByEANMethod = getGetProductsWithOfferByEANMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProductsWithOfferByEAN"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetProductsWithOfferByEAN"))
              .build();
        }
      }
    }
    return getGetProductsWithOfferByEANMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse> getGetProductsByPermalinkMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProductsByPermalink",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse> getGetProductsByPermalinkMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse> getGetProductsByPermalinkMethod;
    if ((getGetProductsByPermalinkMethod = ProductHubFrontDataGrpc.getGetProductsByPermalinkMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetProductsByPermalinkMethod = ProductHubFrontDataGrpc.getGetProductsByPermalinkMethod) == null) {
          ProductHubFrontDataGrpc.getGetProductsByPermalinkMethod = getGetProductsByPermalinkMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProductsByPermalink"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetProductsByPermalink"))
              .build();
        }
      }
    }
    return getGetProductsByPermalinkMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse> getGetProductsWithOfferByPermalinkMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProductsWithOfferByPermalink",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse> getGetProductsWithOfferByPermalinkMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse> getGetProductsWithOfferByPermalinkMethod;
    if ((getGetProductsWithOfferByPermalinkMethod = ProductHubFrontDataGrpc.getGetProductsWithOfferByPermalinkMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetProductsWithOfferByPermalinkMethod = ProductHubFrontDataGrpc.getGetProductsWithOfferByPermalinkMethod) == null) {
          ProductHubFrontDataGrpc.getGetProductsWithOfferByPermalinkMethod = getGetProductsWithOfferByPermalinkMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProductsWithOfferByPermalink"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetProductsWithOfferByPermalink"))
              .build();
        }
      }
    }
    return getGetProductsWithOfferByPermalinkMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse> getGetOffersBySKUANDStoreIDMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetOffersBySKUANDStoreID",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse> getGetOffersBySKUANDStoreIDMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse> getGetOffersBySKUANDStoreIDMethod;
    if ((getGetOffersBySKUANDStoreIDMethod = ProductHubFrontDataGrpc.getGetOffersBySKUANDStoreIDMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetOffersBySKUANDStoreIDMethod = ProductHubFrontDataGrpc.getGetOffersBySKUANDStoreIDMethod) == null) {
          ProductHubFrontDataGrpc.getGetOffersBySKUANDStoreIDMethod = getGetOffersBySKUANDStoreIDMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetOffersBySKUANDStoreID"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetOffersBySKUANDStoreID"))
              .build();
        }
      }
    }
    return getGetOffersBySKUANDStoreIDMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> getGetStocksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStocks",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> getGetStocksMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> getGetStocksMethod;
    if ((getGetStocksMethod = ProductHubFrontDataGrpc.getGetStocksMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetStocksMethod = ProductHubFrontDataGrpc.getGetStocksMethod) == null) {
          ProductHubFrontDataGrpc.getGetStocksMethod = getGetStocksMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStocks"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetStocks"))
              .build();
        }
      }
    }
    return getGetStocksMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByRetailerSKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> getGetStocksByRetailerSKUMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStocksByRetailerSKU",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByRetailerSKURequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByRetailerSKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> getGetStocksByRetailerSKUMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByRetailerSKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> getGetStocksByRetailerSKUMethod;
    if ((getGetStocksByRetailerSKUMethod = ProductHubFrontDataGrpc.getGetStocksByRetailerSKUMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetStocksByRetailerSKUMethod = ProductHubFrontDataGrpc.getGetStocksByRetailerSKUMethod) == null) {
          ProductHubFrontDataGrpc.getGetStocksByRetailerSKUMethod = getGetStocksByRetailerSKUMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByRetailerSKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStocksByRetailerSKU"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByRetailerSKURequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetStocksByRetailerSKU"))
              .build();
        }
      }
    }
    return getGetStocksByRetailerSKUMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByOfferIdsRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> getGetStocksByOfferIDsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStocksByOfferIDs",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByOfferIdsRequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByOfferIdsRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> getGetStocksByOfferIDsMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByOfferIdsRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> getGetStocksByOfferIDsMethod;
    if ((getGetStocksByOfferIDsMethod = ProductHubFrontDataGrpc.getGetStocksByOfferIDsMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetStocksByOfferIDsMethod = ProductHubFrontDataGrpc.getGetStocksByOfferIDsMethod) == null) {
          ProductHubFrontDataGrpc.getGetStocksByOfferIDsMethod = getGetStocksByOfferIDsMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByOfferIdsRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStocksByOfferIDs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByOfferIdsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetStocksByOfferIDs"))
              .build();
        }
      }
    }
    return getGetStocksByOfferIDsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse> getGetOffersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetOffers",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse> getGetOffersMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse> getGetOffersMethod;
    if ((getGetOffersMethod = ProductHubFrontDataGrpc.getGetOffersMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetOffersMethod = ProductHubFrontDataGrpc.getGetOffersMethod) == null) {
          ProductHubFrontDataGrpc.getGetOffersMethod = getGetOffersMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetOffers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetOffers"))
              .build();
        }
      }
    }
    return getGetOffersMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDResponse> getGetOffersByOriginRetailerSKUANDRetailerIDMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetOffersByOriginRetailerSKUANDRetailerID",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDRequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDResponse> getGetOffersByOriginRetailerSKUANDRetailerIDMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDResponse> getGetOffersByOriginRetailerSKUANDRetailerIDMethod;
    if ((getGetOffersByOriginRetailerSKUANDRetailerIDMethod = ProductHubFrontDataGrpc.getGetOffersByOriginRetailerSKUANDRetailerIDMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetOffersByOriginRetailerSKUANDRetailerIDMethod = ProductHubFrontDataGrpc.getGetOffersByOriginRetailerSKUANDRetailerIDMethod) == null) {
          ProductHubFrontDataGrpc.getGetOffersByOriginRetailerSKUANDRetailerIDMethod = getGetOffersByOriginRetailerSKUANDRetailerIDMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetOffersByOriginRetailerSKUANDRetailerID"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetOffersByOriginRetailerSKUANDRetailerID"))
              .build();
        }
      }
    }
    return getGetOffersByOriginRetailerSKUANDRetailerIDMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse> getGetPricesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPrices",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse> getGetPricesMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse> getGetPricesMethod;
    if ((getGetPricesMethod = ProductHubFrontDataGrpc.getGetPricesMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetPricesMethod = ProductHubFrontDataGrpc.getGetPricesMethod) == null) {
          ProductHubFrontDataGrpc.getGetPricesMethod = getGetPricesMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetPrices"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetPrices"))
              .build();
        }
      }
    }
    return getGetPricesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKUResponse> getGetPricesByRetailerSKUMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPricesByRetailerSKU",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKURequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKUResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKUResponse> getGetPricesByRetailerSKUMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKUResponse> getGetPricesByRetailerSKUMethod;
    if ((getGetPricesByRetailerSKUMethod = ProductHubFrontDataGrpc.getGetPricesByRetailerSKUMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetPricesByRetailerSKUMethod = ProductHubFrontDataGrpc.getGetPricesByRetailerSKUMethod) == null) {
          ProductHubFrontDataGrpc.getGetPricesByRetailerSKUMethod = getGetPricesByRetailerSKUMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKUResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetPricesByRetailerSKU"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKURequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKUResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetPricesByRetailerSKU"))
              .build();
        }
      }
    }
    return getGetPricesByRetailerSKUMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKUResponse> getIsActiveBySKUMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "IsActiveBySKU",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKURequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKUResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKUResponse> getIsActiveBySKUMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKUResponse> getIsActiveBySKUMethod;
    if ((getIsActiveBySKUMethod = ProductHubFrontDataGrpc.getIsActiveBySKUMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getIsActiveBySKUMethod = ProductHubFrontDataGrpc.getIsActiveBySKUMethod) == null) {
          ProductHubFrontDataGrpc.getIsActiveBySKUMethod = getIsActiveBySKUMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKUResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "IsActiveBySKU"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKURequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKUResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("IsActiveBySKU"))
              .build();
        }
      }
    }
    return getIsActiveBySKUMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKUResponse> getIsActiveByRetailerSKUMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "IsActiveByRetailerSKU",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKURequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKUResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKUResponse> getIsActiveByRetailerSKUMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKUResponse> getIsActiveByRetailerSKUMethod;
    if ((getIsActiveByRetailerSKUMethod = ProductHubFrontDataGrpc.getIsActiveByRetailerSKUMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getIsActiveByRetailerSKUMethod = ProductHubFrontDataGrpc.getIsActiveByRetailerSKUMethod) == null) {
          ProductHubFrontDataGrpc.getIsActiveByRetailerSKUMethod = getIsActiveByRetailerSKUMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKUResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "IsActiveByRetailerSKU"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKURequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKUResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("IsActiveByRetailerSKU"))
              .build();
        }
      }
    }
    return getIsActiveByRetailerSKUMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKUResponse> getIsActiveGroupsBySKUMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "IsActiveGroupsBySKU",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKURequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKUResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKUResponse> getIsActiveGroupsBySKUMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKUResponse> getIsActiveGroupsBySKUMethod;
    if ((getIsActiveGroupsBySKUMethod = ProductHubFrontDataGrpc.getIsActiveGroupsBySKUMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getIsActiveGroupsBySKUMethod = ProductHubFrontDataGrpc.getIsActiveGroupsBySKUMethod) == null) {
          ProductHubFrontDataGrpc.getIsActiveGroupsBySKUMethod = getIsActiveGroupsBySKUMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKUResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "IsActiveGroupsBySKU"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKURequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKUResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("IsActiveGroupsBySKU"))
              .build();
        }
      }
    }
    return getIsActiveGroupsBySKUMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsResponse> getGetGroupsByIDsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetGroupsByIDs",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsRequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsResponse> getGetGroupsByIDsMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsResponse> getGetGroupsByIDsMethod;
    if ((getGetGroupsByIDsMethod = ProductHubFrontDataGrpc.getGetGroupsByIDsMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetGroupsByIDsMethod = ProductHubFrontDataGrpc.getGetGroupsByIDsMethod) == null) {
          ProductHubFrontDataGrpc.getGetGroupsByIDsMethod = getGetGroupsByIDsMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetGroupsByIDs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetGroupsByIDs"))
              .build();
        }
      }
    }
    return getGetGroupsByIDsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKUResponse> getGetGroupsByRetailerSKUMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetGroupsByRetailerSKU",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKURequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKUResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKUResponse> getGetGroupsByRetailerSKUMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKUResponse> getGetGroupsByRetailerSKUMethod;
    if ((getGetGroupsByRetailerSKUMethod = ProductHubFrontDataGrpc.getGetGroupsByRetailerSKUMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetGroupsByRetailerSKUMethod = ProductHubFrontDataGrpc.getGetGroupsByRetailerSKUMethod) == null) {
          ProductHubFrontDataGrpc.getGetGroupsByRetailerSKUMethod = getGetGroupsByRetailerSKUMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKUResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetGroupsByRetailerSKU"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKURequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKUResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetGroupsByRetailerSKU"))
              .build();
        }
      }
    }
    return getGetGroupsByRetailerSKUMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKUResponse> getGetProductGroupsWithOfferBySKUMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProductGroupsWithOfferBySKU",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKURequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKUResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKUResponse> getGetProductGroupsWithOfferBySKUMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKUResponse> getGetProductGroupsWithOfferBySKUMethod;
    if ((getGetProductGroupsWithOfferBySKUMethod = ProductHubFrontDataGrpc.getGetProductGroupsWithOfferBySKUMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetProductGroupsWithOfferBySKUMethod = ProductHubFrontDataGrpc.getGetProductGroupsWithOfferBySKUMethod) == null) {
          ProductHubFrontDataGrpc.getGetProductGroupsWithOfferBySKUMethod = getGetProductGroupsWithOfferBySKUMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKUResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProductGroupsWithOfferBySKU"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKURequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKUResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetProductGroupsWithOfferBySKU"))
              .build();
        }
      }
    }
    return getGetProductGroupsWithOfferBySKUMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKUResponse> getGetProductGroupsWithOfferByRetailerSKUMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProductGroupsWithOfferByRetailerSKU",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKURequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKUResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKUResponse> getGetProductGroupsWithOfferByRetailerSKUMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKUResponse> getGetProductGroupsWithOfferByRetailerSKUMethod;
    if ((getGetProductGroupsWithOfferByRetailerSKUMethod = ProductHubFrontDataGrpc.getGetProductGroupsWithOfferByRetailerSKUMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetProductGroupsWithOfferByRetailerSKUMethod = ProductHubFrontDataGrpc.getGetProductGroupsWithOfferByRetailerSKUMethod) == null) {
          ProductHubFrontDataGrpc.getGetProductGroupsWithOfferByRetailerSKUMethod = getGetProductGroupsWithOfferByRetailerSKUMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKUResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProductGroupsWithOfferByRetailerSKU"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKURequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKUResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetProductGroupsWithOfferByRetailerSKU"))
              .build();
        }
      }
    }
    return getGetProductGroupsWithOfferByRetailerSKUMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ProductHubFrontDataStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductHubFrontDataStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductHubFrontDataStub>() {
        @java.lang.Override
        public ProductHubFrontDataStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductHubFrontDataStub(channel, callOptions);
        }
      };
    return ProductHubFrontDataStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ProductHubFrontDataBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductHubFrontDataBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductHubFrontDataBlockingStub>() {
        @java.lang.Override
        public ProductHubFrontDataBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductHubFrontDataBlockingStub(channel, callOptions);
        }
      };
    return ProductHubFrontDataBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ProductHubFrontDataFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductHubFrontDataFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductHubFrontDataFutureStub>() {
        @java.lang.Override
        public ProductHubFrontDataFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductHubFrontDataFutureStub(channel, callOptions);
        }
      };
    return ProductHubFrontDataFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * slack:
   *    #product-hub
   * base:
   *     base-product-hub.paas-content-product-hub:3009
   * local:
   *     base-product-hub.paas-content-product-hub.svc.cluster.local:3009
   * swagger prod:
   *     https://paas-content-product-hub.sbmt.io/api
   * swagger stg:
   *     https://paas-content-product-hub.gw-stage.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-product-hub.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-product-hub.gw-stage.sbmt.io:443
   * description:
   *    Фронтовый сервис product-hub возвращающий описание товаров (product+attribute),
   *    товарное вредложение (offer), цену (price) и остатки (stock).
   *    Полный набор атрибутов и флагов для всех сущностей описан здесь https://wiki.sbmt.io/display/CP/Product+Hub
   *    Предназначет для real-time нагрузки.
   *    Основные параметры запроса:
   *       sku - сбермартовский идентификатор описания товара
   *       store_id - сбермартовский идентификатор магазина
   *       tenant_id - сбермартовский идентификатор площадки
   *       retailer_id - сбермартовский идентификатор ритейлера
   *       retailer_sku - ритейлерский идентификатор товарного предложения
   * </pre>
   */
  public static abstract class ProductHubFrontDataImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Возвращает описание товаров по sku в виде атрибутов
     * Обязательные параметры:
     *    sku - идентификатор описания
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"sku": ["16296"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     *    Вернуть только определенные атрибуты:
     *        grpcurl -d '{"sku": ["16296"], "display_attributes": [{"keys":["brand", "image"]}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     *    Вернуть только определенную группу атрибутов размеченную флагом:
     *        grpcurl -d '{"sku": ["16296"], "display_attributes": [{"flags":["show_as_characteristic"]}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     * </pre>
     */
    public void getProductsBySKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductsBySKUMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает описание товаров c ценой, стоком и товарным предложением по sku
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"sku": ["16296"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferBySKU
     * </pre>
     */
    public void getProductsWithOfferBySKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductsWithOfferBySKUMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает описание товаров c ценой, стоком и товарным предложением по offer_id (идентификатор базы mysql таблички offers)
     * Обязательные параметры:
     *    offer_ids - идентификатор офера
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"offer_ids": ["231461"], "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByOfferIDs
     * </pre>
     */
    public void getProductsWithOfferByOfferIDs(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductsWithOfferByOfferIDsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает описание товаров по EAN
     * Обязательные параметры:
     *    EAN - штрихкод
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"ean": ["4000417048103"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsByEAN
     * </pre>
     */
    public void getProductsByEAN(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductsByEANMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает описание товаров c ценой, стоком и товарным предложением по EAN
     * Обязательные параметры:
     *    EAN - штрихкод
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"ean": ["4000417048103"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByEAN
     * </pre>
     */
    public void getProductsWithOfferByEAN(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductsWithOfferByEANMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает описание товаров по permalink товаров (атрибут описания permalink)
     * Обязательные параметры:
     *    permalink - пермалинк товара
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"permalink": ["shokolad-ritter-sport-mini-bunter-mix-7-vkusov-16-67-g-h-84-sht-691a0cc"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsByPermalink
     * </pre>
     */
    public void getProductsByPermalink(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductsByPermalinkMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает описание товаров c ценой, стоком и товарным предложением по permalink товаров (атрибут описания permalink)
     * Обязательные параметры:
     *    permalink - пермалинк товара
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"permalink": ["shokolad-ritter-sport-mini-bunter-mix-7-vkusov-16-67-g-h-84-sht-691a0cc"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByPermalink
     * </pre>
     */
    public void getProductsWithOfferByPermalink(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductsWithOfferByPermalinkMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает товарные предложения по sku описания + store_id
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть товарное предложение (offer):
     *        grpcurl -d '{"offers": [{"sku": "16296", "store_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetOffersBySKUANDStoreID
     * </pre>
     */
    public void getOffersBySKUANDStoreID(product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetOffersBySKUANDStoreIDMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает остатки по sku описания + store_id
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть остатки (stock):
     *        grpcurl -d '{"stocks": [{"sku": "16296", "store_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetStocks
     * </pre>
     */
    public void getStocks(product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStocksMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает остатки по retailer_sku + store_id
     * Обязательные параметры:
     *    retailer_sku - ритейлерский идентификатор товарного предложения
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть остатки (stock):
     *        grpcurl -d '{"stocks": [{"retailer_sku": "1000382026", "store_id": 2402}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetStocksByRetailerSKU
     * </pre>
     */
    public void getStocksByRetailerSKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByRetailerSKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStocksByRetailerSKUMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает остатки по offer_ids
     * Обязательные параметры:
     *    offer_ids - список оффер id
     * Примеры:
     *    Вернуть остатки (stock):
     *        grpcurl -d '{"offer_ids": [1000382026, 1000382027]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetStocksByOfferIDs
     * </pre>
     */
    public void getStocksByOfferIDs(product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByOfferIdsRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStocksByOfferIDsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает товарные предложения по retailer_sku + retailer_id
     * Обязательные параметры:
     *    retailer_sku - идентификатор товарного предлоежния ритейлера
     *    retailer_id - идентификатор ритейлера
     * Примеры:
     *    Вернуть товарное предложение (offer):
     *        grpcurl -d '{"offers": [{"retailer_sku": "337582", "retailer_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetOffers
     * </pre>
     */
    public void getOffers(product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetOffersMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает товарные предложения по origin_retailer_sku + retailer_id
     * Обязательные параметры:
     *    origin_retailer_sku - оригинальный идентификатор товарного предлоежния ритейлера
     *    retailer_id - идентификатор ритейлера
     * Примеры:
     *    Вернуть товарное предложение (offer):
     *        grpcurl -d '{"offers": [{"origin_retailer_sku": "337582", "retailer_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetOffersByOriginRetailerSKUANDRetailerID
     * </pre>
     */
    public void getOffersByOriginRetailerSKUANDRetailerID(product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetOffersByOriginRetailerSKUANDRetailerIDMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает цены по sku + store_id + tenant_id
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть цены (price):
     *        grpcurl -d '{"prices": [{"sku": ["16296"], "store_id": "1", "tenant_id": "sbermarket"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetPrices
     * </pre>
     */
    public void getPrices(product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetPricesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает цены по retailer_sku + store_id + tenant_id
     * Обязательные параметры:
     *    retailer_sku - идентификатор ретейлера
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть цены (price):
     *        grpcurl -d '{"prices": [{"retailer_sku": "1", "store_id": "1", "tenant_id": "sbermarket"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetPricesByRetailerSKU
     * </pre>
     */
    public void getPricesByRetailerSKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKUResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetPricesByRetailerSKUMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает активность товаров по sku
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * </pre>
     */
    public void isActiveBySKU(product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKUResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getIsActiveBySKUMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает активность товаров по retailer_sku
     * Обязательные параметры:
     *    retailer_sku - идентификатор ретейлера
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * </pre>
     */
    public void isActiveByRetailerSKU(product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKUResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getIsActiveByRetailerSKUMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает активность хотя бы 1 группы по sku
     * Обязательные параметры:
     *    []sku - идентификатор ретейлера
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть активные группы по sku:
     *        grpcurl -d '{"sku": [{"sku": ["1"], "store_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.IsActiveGroupsBySKU
     * </pre>
     */
    public void isActiveGroupsBySKU(product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKUResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getIsActiveGroupsBySKUMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает группы sku по group_id
     * Обязательные параметры:
     *    retailer_id - идентификатор ретейлера
     *    group_id - идентификатор группы
     * Примеры:
     *    Вернуть группы по id:
     *        grpcurl -d '{"groups": [{"retailer_sku": "1", "group_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetGroupsByIDs
     * </pre>
     */
    public void getGroupsByIDs(product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetGroupsByIDsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает группы sku по retailer_sku
     * Обязательные параметры:
     *    retailer_id - идентификатор ретейлера
     *    retailer_sku - идентификатор sku
     * Примеры:
     *    Вернуть группы по связки входных параметров:
     *        grpcurl -d '{"groups": [{"retailer_sku": "1", "retailer_sku": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetGroupsByRetailerSKU
     * </pre>
     */
    public void getGroupsByRetailerSKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKUResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetGroupsByRetailerSKUMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает группы + описание товаров c ценой, стоком и товарным предложением по sku
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"sku": ["68496"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductGroupsWithOfferBySKU
     * </pre>
     */
    public void getProductGroupsWithOfferBySKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKUResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductGroupsWithOfferBySKUMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает группы + описание товаров c ценой, стоком и товарным предложением по retailer_sku
     * Обязательные параметры:
     *    retailer_sku - идентификатор описания ретейлера
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"retailer_sku": ["602440"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductGroupsWithOfferByRetailerSKU
     * </pre>
     */
    public void getProductGroupsWithOfferByRetailerSKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKUResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductGroupsWithOfferByRetailerSKUMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetProductsBySKUMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse>(
                  this, METHODID_GET_PRODUCTS_BY_SKU)))
          .addMethod(
            getGetProductsWithOfferBySKUMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse>(
                  this, METHODID_GET_PRODUCTS_WITH_OFFER_BY_SKU)))
          .addMethod(
            getGetProductsWithOfferByOfferIDsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse>(
                  this, METHODID_GET_PRODUCTS_WITH_OFFER_BY_OFFER_IDS)))
          .addMethod(
            getGetProductsByEANMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse>(
                  this, METHODID_GET_PRODUCTS_BY_EAN)))
          .addMethod(
            getGetProductsWithOfferByEANMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse>(
                  this, METHODID_GET_PRODUCTS_WITH_OFFER_BY_EAN)))
          .addMethod(
            getGetProductsByPermalinkMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse>(
                  this, METHODID_GET_PRODUCTS_BY_PERMALINK)))
          .addMethod(
            getGetProductsWithOfferByPermalinkMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse>(
                  this, METHODID_GET_PRODUCTS_WITH_OFFER_BY_PERMALINK)))
          .addMethod(
            getGetOffersBySKUANDStoreIDMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse>(
                  this, METHODID_GET_OFFERS_BY_SKUANDSTORE_ID)))
          .addMethod(
            getGetStocksMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse>(
                  this, METHODID_GET_STOCKS)))
          .addMethod(
            getGetStocksByRetailerSKUMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByRetailerSKURequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse>(
                  this, METHODID_GET_STOCKS_BY_RETAILER_SKU)))
          .addMethod(
            getGetStocksByOfferIDsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByOfferIdsRequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse>(
                  this, METHODID_GET_STOCKS_BY_OFFER_IDS)))
          .addMethod(
            getGetOffersMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse>(
                  this, METHODID_GET_OFFERS)))
          .addMethod(
            getGetOffersByOriginRetailerSKUANDRetailerIDMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDRequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDResponse>(
                  this, METHODID_GET_OFFERS_BY_ORIGIN_RETAILER_SKUANDRETAILER_ID)))
          .addMethod(
            getGetPricesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse>(
                  this, METHODID_GET_PRICES)))
          .addMethod(
            getGetPricesByRetailerSKUMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKURequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKUResponse>(
                  this, METHODID_GET_PRICES_BY_RETAILER_SKU)))
          .addMethod(
            getIsActiveBySKUMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKURequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKUResponse>(
                  this, METHODID_IS_ACTIVE_BY_SKU)))
          .addMethod(
            getIsActiveByRetailerSKUMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKURequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKUResponse>(
                  this, METHODID_IS_ACTIVE_BY_RETAILER_SKU)))
          .addMethod(
            getIsActiveGroupsBySKUMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKURequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKUResponse>(
                  this, METHODID_IS_ACTIVE_GROUPS_BY_SKU)))
          .addMethod(
            getGetGroupsByIDsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsRequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsResponse>(
                  this, METHODID_GET_GROUPS_BY_IDS)))
          .addMethod(
            getGetGroupsByRetailerSKUMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKURequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKUResponse>(
                  this, METHODID_GET_GROUPS_BY_RETAILER_SKU)))
          .addMethod(
            getGetProductGroupsWithOfferBySKUMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKURequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKUResponse>(
                  this, METHODID_GET_PRODUCT_GROUPS_WITH_OFFER_BY_SKU)))
          .addMethod(
            getGetProductGroupsWithOfferByRetailerSKUMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKURequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKUResponse>(
                  this, METHODID_GET_PRODUCT_GROUPS_WITH_OFFER_BY_RETAILER_SKU)))
          .build();
    }
  }

  /**
   * <pre>
   * slack:
   *    #product-hub
   * base:
   *     base-product-hub.paas-content-product-hub:3009
   * local:
   *     base-product-hub.paas-content-product-hub.svc.cluster.local:3009
   * swagger prod:
   *     https://paas-content-product-hub.sbmt.io/api
   * swagger stg:
   *     https://paas-content-product-hub.gw-stage.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-product-hub.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-product-hub.gw-stage.sbmt.io:443
   * description:
   *    Фронтовый сервис product-hub возвращающий описание товаров (product+attribute),
   *    товарное вредложение (offer), цену (price) и остатки (stock).
   *    Полный набор атрибутов и флагов для всех сущностей описан здесь https://wiki.sbmt.io/display/CP/Product+Hub
   *    Предназначет для real-time нагрузки.
   *    Основные параметры запроса:
   *       sku - сбермартовский идентификатор описания товара
   *       store_id - сбермартовский идентификатор магазина
   *       tenant_id - сбермартовский идентификатор площадки
   *       retailer_id - сбермартовский идентификатор ритейлера
   *       retailer_sku - ритейлерский идентификатор товарного предложения
   * </pre>
   */
  public static final class ProductHubFrontDataStub extends io.grpc.stub.AbstractAsyncStub<ProductHubFrontDataStub> {
    private ProductHubFrontDataStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductHubFrontDataStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductHubFrontDataStub(channel, callOptions);
    }

    /**
     * <pre>
     * Возвращает описание товаров по sku в виде атрибутов
     * Обязательные параметры:
     *    sku - идентификатор описания
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"sku": ["16296"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     *    Вернуть только определенные атрибуты:
     *        grpcurl -d '{"sku": ["16296"], "display_attributes": [{"keys":["brand", "image"]}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     *    Вернуть только определенную группу атрибутов размеченную флагом:
     *        grpcurl -d '{"sku": ["16296"], "display_attributes": [{"flags":["show_as_characteristic"]}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     * </pre>
     */
    public void getProductsBySKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductsBySKUMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает описание товаров c ценой, стоком и товарным предложением по sku
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"sku": ["16296"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferBySKU
     * </pre>
     */
    public void getProductsWithOfferBySKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductsWithOfferBySKUMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает описание товаров c ценой, стоком и товарным предложением по offer_id (идентификатор базы mysql таблички offers)
     * Обязательные параметры:
     *    offer_ids - идентификатор офера
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"offer_ids": ["231461"], "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByOfferIDs
     * </pre>
     */
    public void getProductsWithOfferByOfferIDs(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductsWithOfferByOfferIDsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает описание товаров по EAN
     * Обязательные параметры:
     *    EAN - штрихкод
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"ean": ["4000417048103"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsByEAN
     * </pre>
     */
    public void getProductsByEAN(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductsByEANMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает описание товаров c ценой, стоком и товарным предложением по EAN
     * Обязательные параметры:
     *    EAN - штрихкод
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"ean": ["4000417048103"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByEAN
     * </pre>
     */
    public void getProductsWithOfferByEAN(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductsWithOfferByEANMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает описание товаров по permalink товаров (атрибут описания permalink)
     * Обязательные параметры:
     *    permalink - пермалинк товара
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"permalink": ["shokolad-ritter-sport-mini-bunter-mix-7-vkusov-16-67-g-h-84-sht-691a0cc"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsByPermalink
     * </pre>
     */
    public void getProductsByPermalink(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductsByPermalinkMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает описание товаров c ценой, стоком и товарным предложением по permalink товаров (атрибут описания permalink)
     * Обязательные параметры:
     *    permalink - пермалинк товара
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"permalink": ["shokolad-ritter-sport-mini-bunter-mix-7-vkusov-16-67-g-h-84-sht-691a0cc"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByPermalink
     * </pre>
     */
    public void getProductsWithOfferByPermalink(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductsWithOfferByPermalinkMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает товарные предложения по sku описания + store_id
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть товарное предложение (offer):
     *        grpcurl -d '{"offers": [{"sku": "16296", "store_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetOffersBySKUANDStoreID
     * </pre>
     */
    public void getOffersBySKUANDStoreID(product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetOffersBySKUANDStoreIDMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает остатки по sku описания + store_id
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть остатки (stock):
     *        grpcurl -d '{"stocks": [{"sku": "16296", "store_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetStocks
     * </pre>
     */
    public void getStocks(product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStocksMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает остатки по retailer_sku + store_id
     * Обязательные параметры:
     *    retailer_sku - ритейлерский идентификатор товарного предложения
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть остатки (stock):
     *        grpcurl -d '{"stocks": [{"retailer_sku": "1000382026", "store_id": 2402}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetStocksByRetailerSKU
     * </pre>
     */
    public void getStocksByRetailerSKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByRetailerSKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStocksByRetailerSKUMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает остатки по offer_ids
     * Обязательные параметры:
     *    offer_ids - список оффер id
     * Примеры:
     *    Вернуть остатки (stock):
     *        grpcurl -d '{"offer_ids": [1000382026, 1000382027]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetStocksByOfferIDs
     * </pre>
     */
    public void getStocksByOfferIDs(product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByOfferIdsRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStocksByOfferIDsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает товарные предложения по retailer_sku + retailer_id
     * Обязательные параметры:
     *    retailer_sku - идентификатор товарного предлоежния ритейлера
     *    retailer_id - идентификатор ритейлера
     * Примеры:
     *    Вернуть товарное предложение (offer):
     *        grpcurl -d '{"offers": [{"retailer_sku": "337582", "retailer_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetOffers
     * </pre>
     */
    public void getOffers(product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetOffersMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает товарные предложения по origin_retailer_sku + retailer_id
     * Обязательные параметры:
     *    origin_retailer_sku - оригинальный идентификатор товарного предлоежния ритейлера
     *    retailer_id - идентификатор ритейлера
     * Примеры:
     *    Вернуть товарное предложение (offer):
     *        grpcurl -d '{"offers": [{"origin_retailer_sku": "337582", "retailer_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetOffersByOriginRetailerSKUANDRetailerID
     * </pre>
     */
    public void getOffersByOriginRetailerSKUANDRetailerID(product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetOffersByOriginRetailerSKUANDRetailerIDMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает цены по sku + store_id + tenant_id
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть цены (price):
     *        grpcurl -d '{"prices": [{"sku": ["16296"], "store_id": "1", "tenant_id": "sbermarket"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetPrices
     * </pre>
     */
    public void getPrices(product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetPricesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает цены по retailer_sku + store_id + tenant_id
     * Обязательные параметры:
     *    retailer_sku - идентификатор ретейлера
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть цены (price):
     *        grpcurl -d '{"prices": [{"retailer_sku": "1", "store_id": "1", "tenant_id": "sbermarket"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetPricesByRetailerSKU
     * </pre>
     */
    public void getPricesByRetailerSKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKUResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetPricesByRetailerSKUMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает активность товаров по sku
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * </pre>
     */
    public void isActiveBySKU(product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKUResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getIsActiveBySKUMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает активность товаров по retailer_sku
     * Обязательные параметры:
     *    retailer_sku - идентификатор ретейлера
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * </pre>
     */
    public void isActiveByRetailerSKU(product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKUResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getIsActiveByRetailerSKUMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает активность хотя бы 1 группы по sku
     * Обязательные параметры:
     *    []sku - идентификатор ретейлера
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть активные группы по sku:
     *        grpcurl -d '{"sku": [{"sku": ["1"], "store_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.IsActiveGroupsBySKU
     * </pre>
     */
    public void isActiveGroupsBySKU(product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKUResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getIsActiveGroupsBySKUMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает группы sku по group_id
     * Обязательные параметры:
     *    retailer_id - идентификатор ретейлера
     *    group_id - идентификатор группы
     * Примеры:
     *    Вернуть группы по id:
     *        grpcurl -d '{"groups": [{"retailer_sku": "1", "group_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetGroupsByIDs
     * </pre>
     */
    public void getGroupsByIDs(product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetGroupsByIDsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает группы sku по retailer_sku
     * Обязательные параметры:
     *    retailer_id - идентификатор ретейлера
     *    retailer_sku - идентификатор sku
     * Примеры:
     *    Вернуть группы по связки входных параметров:
     *        grpcurl -d '{"groups": [{"retailer_sku": "1", "retailer_sku": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetGroupsByRetailerSKU
     * </pre>
     */
    public void getGroupsByRetailerSKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKUResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetGroupsByRetailerSKUMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает группы + описание товаров c ценой, стоком и товарным предложением по sku
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"sku": ["68496"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductGroupsWithOfferBySKU
     * </pre>
     */
    public void getProductGroupsWithOfferBySKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKUResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductGroupsWithOfferBySKUMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает группы + описание товаров c ценой, стоком и товарным предложением по retailer_sku
     * Обязательные параметры:
     *    retailer_sku - идентификатор описания ретейлера
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"retailer_sku": ["602440"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductGroupsWithOfferByRetailerSKU
     * </pre>
     */
    public void getProductGroupsWithOfferByRetailerSKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKUResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductGroupsWithOfferByRetailerSKUMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * slack:
   *    #product-hub
   * base:
   *     base-product-hub.paas-content-product-hub:3009
   * local:
   *     base-product-hub.paas-content-product-hub.svc.cluster.local:3009
   * swagger prod:
   *     https://paas-content-product-hub.sbmt.io/api
   * swagger stg:
   *     https://paas-content-product-hub.gw-stage.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-product-hub.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-product-hub.gw-stage.sbmt.io:443
   * description:
   *    Фронтовый сервис product-hub возвращающий описание товаров (product+attribute),
   *    товарное вредложение (offer), цену (price) и остатки (stock).
   *    Полный набор атрибутов и флагов для всех сущностей описан здесь https://wiki.sbmt.io/display/CP/Product+Hub
   *    Предназначет для real-time нагрузки.
   *    Основные параметры запроса:
   *       sku - сбермартовский идентификатор описания товара
   *       store_id - сбермартовский идентификатор магазина
   *       tenant_id - сбермартовский идентификатор площадки
   *       retailer_id - сбермартовский идентификатор ритейлера
   *       retailer_sku - ритейлерский идентификатор товарного предложения
   * </pre>
   */
  public static final class ProductHubFrontDataBlockingStub extends io.grpc.stub.AbstractBlockingStub<ProductHubFrontDataBlockingStub> {
    private ProductHubFrontDataBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductHubFrontDataBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductHubFrontDataBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Возвращает описание товаров по sku в виде атрибутов
     * Обязательные параметры:
     *    sku - идентификатор описания
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"sku": ["16296"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     *    Вернуть только определенные атрибуты:
     *        grpcurl -d '{"sku": ["16296"], "display_attributes": [{"keys":["brand", "image"]}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     *    Вернуть только определенную группу атрибутов размеченную флагом:
     *        grpcurl -d '{"sku": ["16296"], "display_attributes": [{"flags":["show_as_characteristic"]}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse getProductsBySKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductsBySKUMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает описание товаров c ценой, стоком и товарным предложением по sku
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"sku": ["16296"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferBySKU
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse getProductsWithOfferBySKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductsWithOfferBySKUMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает описание товаров c ценой, стоком и товарным предложением по offer_id (идентификатор базы mysql таблички offers)
     * Обязательные параметры:
     *    offer_ids - идентификатор офера
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"offer_ids": ["231461"], "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByOfferIDs
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse getProductsWithOfferByOfferIDs(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductsWithOfferByOfferIDsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает описание товаров по EAN
     * Обязательные параметры:
     *    EAN - штрихкод
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"ean": ["4000417048103"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsByEAN
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse getProductsByEAN(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductsByEANMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает описание товаров c ценой, стоком и товарным предложением по EAN
     * Обязательные параметры:
     *    EAN - штрихкод
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"ean": ["4000417048103"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByEAN
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse getProductsWithOfferByEAN(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductsWithOfferByEANMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает описание товаров по permalink товаров (атрибут описания permalink)
     * Обязательные параметры:
     *    permalink - пермалинк товара
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"permalink": ["shokolad-ritter-sport-mini-bunter-mix-7-vkusov-16-67-g-h-84-sht-691a0cc"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsByPermalink
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse getProductsByPermalink(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductsByPermalinkMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает описание товаров c ценой, стоком и товарным предложением по permalink товаров (атрибут описания permalink)
     * Обязательные параметры:
     *    permalink - пермалинк товара
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"permalink": ["shokolad-ritter-sport-mini-bunter-mix-7-vkusov-16-67-g-h-84-sht-691a0cc"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByPermalink
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse getProductsWithOfferByPermalink(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductsWithOfferByPermalinkMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает товарные предложения по sku описания + store_id
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть товарное предложение (offer):
     *        grpcurl -d '{"offers": [{"sku": "16296", "store_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetOffersBySKUANDStoreID
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse getOffersBySKUANDStoreID(product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetOffersBySKUANDStoreIDMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает остатки по sku описания + store_id
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть остатки (stock):
     *        grpcurl -d '{"stocks": [{"sku": "16296", "store_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetStocks
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse getStocks(product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStocksMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает остатки по retailer_sku + store_id
     * Обязательные параметры:
     *    retailer_sku - ритейлерский идентификатор товарного предложения
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть остатки (stock):
     *        grpcurl -d '{"stocks": [{"retailer_sku": "1000382026", "store_id": 2402}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetStocksByRetailerSKU
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse getStocksByRetailerSKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByRetailerSKURequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStocksByRetailerSKUMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает остатки по offer_ids
     * Обязательные параметры:
     *    offer_ids - список оффер id
     * Примеры:
     *    Вернуть остатки (stock):
     *        grpcurl -d '{"offer_ids": [1000382026, 1000382027]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetStocksByOfferIDs
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse getStocksByOfferIDs(product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByOfferIdsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStocksByOfferIDsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает товарные предложения по retailer_sku + retailer_id
     * Обязательные параметры:
     *    retailer_sku - идентификатор товарного предлоежния ритейлера
     *    retailer_id - идентификатор ритейлера
     * Примеры:
     *    Вернуть товарное предложение (offer):
     *        grpcurl -d '{"offers": [{"retailer_sku": "337582", "retailer_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetOffers
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse getOffers(product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetOffersMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает товарные предложения по origin_retailer_sku + retailer_id
     * Обязательные параметры:
     *    origin_retailer_sku - оригинальный идентификатор товарного предлоежния ритейлера
     *    retailer_id - идентификатор ритейлера
     * Примеры:
     *    Вернуть товарное предложение (offer):
     *        grpcurl -d '{"offers": [{"origin_retailer_sku": "337582", "retailer_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetOffersByOriginRetailerSKUANDRetailerID
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDResponse getOffersByOriginRetailerSKUANDRetailerID(product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetOffersByOriginRetailerSKUANDRetailerIDMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает цены по sku + store_id + tenant_id
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть цены (price):
     *        grpcurl -d '{"prices": [{"sku": ["16296"], "store_id": "1", "tenant_id": "sbermarket"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetPrices
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse getPrices(product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetPricesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает цены по retailer_sku + store_id + tenant_id
     * Обязательные параметры:
     *    retailer_sku - идентификатор ретейлера
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть цены (price):
     *        grpcurl -d '{"prices": [{"retailer_sku": "1", "store_id": "1", "tenant_id": "sbermarket"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetPricesByRetailerSKU
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKUResponse getPricesByRetailerSKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKURequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetPricesByRetailerSKUMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает активность товаров по sku
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKUResponse isActiveBySKU(product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKURequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getIsActiveBySKUMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает активность товаров по retailer_sku
     * Обязательные параметры:
     *    retailer_sku - идентификатор ретейлера
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKUResponse isActiveByRetailerSKU(product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKURequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getIsActiveByRetailerSKUMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает активность хотя бы 1 группы по sku
     * Обязательные параметры:
     *    []sku - идентификатор ретейлера
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть активные группы по sku:
     *        grpcurl -d '{"sku": [{"sku": ["1"], "store_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.IsActiveGroupsBySKU
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKUResponse isActiveGroupsBySKU(product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKURequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getIsActiveGroupsBySKUMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает группы sku по group_id
     * Обязательные параметры:
     *    retailer_id - идентификатор ретейлера
     *    group_id - идентификатор группы
     * Примеры:
     *    Вернуть группы по id:
     *        grpcurl -d '{"groups": [{"retailer_sku": "1", "group_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetGroupsByIDs
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsResponse getGroupsByIDs(product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetGroupsByIDsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает группы sku по retailer_sku
     * Обязательные параметры:
     *    retailer_id - идентификатор ретейлера
     *    retailer_sku - идентификатор sku
     * Примеры:
     *    Вернуть группы по связки входных параметров:
     *        grpcurl -d '{"groups": [{"retailer_sku": "1", "retailer_sku": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetGroupsByRetailerSKU
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKUResponse getGroupsByRetailerSKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKURequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetGroupsByRetailerSKUMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает группы + описание товаров c ценой, стоком и товарным предложением по sku
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"sku": ["68496"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductGroupsWithOfferBySKU
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKUResponse getProductGroupsWithOfferBySKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKURequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductGroupsWithOfferBySKUMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает группы + описание товаров c ценой, стоком и товарным предложением по retailer_sku
     * Обязательные параметры:
     *    retailer_sku - идентификатор описания ретейлера
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"retailer_sku": ["602440"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductGroupsWithOfferByRetailerSKU
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKUResponse getProductGroupsWithOfferByRetailerSKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKURequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductGroupsWithOfferByRetailerSKUMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * slack:
   *    #product-hub
   * base:
   *     base-product-hub.paas-content-product-hub:3009
   * local:
   *     base-product-hub.paas-content-product-hub.svc.cluster.local:3009
   * swagger prod:
   *     https://paas-content-product-hub.sbmt.io/api
   * swagger stg:
   *     https://paas-content-product-hub.gw-stage.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-product-hub.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-product-hub.gw-stage.sbmt.io:443
   * description:
   *    Фронтовый сервис product-hub возвращающий описание товаров (product+attribute),
   *    товарное вредложение (offer), цену (price) и остатки (stock).
   *    Полный набор атрибутов и флагов для всех сущностей описан здесь https://wiki.sbmt.io/display/CP/Product+Hub
   *    Предназначет для real-time нагрузки.
   *    Основные параметры запроса:
   *       sku - сбермартовский идентификатор описания товара
   *       store_id - сбермартовский идентификатор магазина
   *       tenant_id - сбермартовский идентификатор площадки
   *       retailer_id - сбермартовский идентификатор ритейлера
   *       retailer_sku - ритейлерский идентификатор товарного предложения
   * </pre>
   */
  public static final class ProductHubFrontDataFutureStub extends io.grpc.stub.AbstractFutureStub<ProductHubFrontDataFutureStub> {
    private ProductHubFrontDataFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductHubFrontDataFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductHubFrontDataFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Возвращает описание товаров по sku в виде атрибутов
     * Обязательные параметры:
     *    sku - идентификатор описания
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"sku": ["16296"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     *    Вернуть только определенные атрибуты:
     *        grpcurl -d '{"sku": ["16296"], "display_attributes": [{"keys":["brand", "image"]}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     *    Вернуть только определенную группу атрибутов размеченную флагом:
     *        grpcurl -d '{"sku": ["16296"], "display_attributes": [{"flags":["show_as_characteristic"]}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse> getProductsBySKU(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductsBySKUMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает описание товаров c ценой, стоком и товарным предложением по sku
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"sku": ["16296"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferBySKU
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse> getProductsWithOfferBySKU(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductsWithOfferBySKUMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает описание товаров c ценой, стоком и товарным предложением по offer_id (идентификатор базы mysql таблички offers)
     * Обязательные параметры:
     *    offer_ids - идентификатор офера
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"offer_ids": ["231461"], "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByOfferIDs
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse> getProductsWithOfferByOfferIDs(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductsWithOfferByOfferIDsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает описание товаров по EAN
     * Обязательные параметры:
     *    EAN - штрихкод
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"ean": ["4000417048103"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsByEAN
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse> getProductsByEAN(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductsByEANMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает описание товаров c ценой, стоком и товарным предложением по EAN
     * Обязательные параметры:
     *    EAN - штрихкод
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"ean": ["4000417048103"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByEAN
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse> getProductsWithOfferByEAN(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductsWithOfferByEANMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает описание товаров по permalink товаров (атрибут описания permalink)
     * Обязательные параметры:
     *    permalink - пермалинк товара
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"permalink": ["shokolad-ritter-sport-mini-bunter-mix-7-vkusov-16-67-g-h-84-sht-691a0cc"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsByPermalink
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse> getProductsByPermalink(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductsByPermalinkMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает описание товаров c ценой, стоком и товарным предложением по permalink товаров (атрибут описания permalink)
     * Обязательные параметры:
     *    permalink - пермалинк товара
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"permalink": ["shokolad-ritter-sport-mini-bunter-mix-7-vkusov-16-67-g-h-84-sht-691a0cc"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByPermalink
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse> getProductsWithOfferByPermalink(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductsWithOfferByPermalinkMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает товарные предложения по sku описания + store_id
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть товарное предложение (offer):
     *        grpcurl -d '{"offers": [{"sku": "16296", "store_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetOffersBySKUANDStoreID
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse> getOffersBySKUANDStoreID(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetOffersBySKUANDStoreIDMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает остатки по sku описания + store_id
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть остатки (stock):
     *        grpcurl -d '{"stocks": [{"sku": "16296", "store_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetStocks
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> getStocks(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStocksMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает остатки по retailer_sku + store_id
     * Обязательные параметры:
     *    retailer_sku - ритейлерский идентификатор товарного предложения
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть остатки (stock):
     *        grpcurl -d '{"stocks": [{"retailer_sku": "1000382026", "store_id": 2402}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetStocksByRetailerSKU
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> getStocksByRetailerSKU(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByRetailerSKURequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStocksByRetailerSKUMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает остатки по offer_ids
     * Обязательные параметры:
     *    offer_ids - список оффер id
     * Примеры:
     *    Вернуть остатки (stock):
     *        grpcurl -d '{"offer_ids": [1000382026, 1000382027]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetStocksByOfferIDs
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> getStocksByOfferIDs(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByOfferIdsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStocksByOfferIDsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает товарные предложения по retailer_sku + retailer_id
     * Обязательные параметры:
     *    retailer_sku - идентификатор товарного предлоежния ритейлера
     *    retailer_id - идентификатор ритейлера
     * Примеры:
     *    Вернуть товарное предложение (offer):
     *        grpcurl -d '{"offers": [{"retailer_sku": "337582", "retailer_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetOffers
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse> getOffers(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetOffersMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает товарные предложения по origin_retailer_sku + retailer_id
     * Обязательные параметры:
     *    origin_retailer_sku - оригинальный идентификатор товарного предлоежния ритейлера
     *    retailer_id - идентификатор ритейлера
     * Примеры:
     *    Вернуть товарное предложение (offer):
     *        grpcurl -d '{"offers": [{"origin_retailer_sku": "337582", "retailer_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetOffersByOriginRetailerSKUANDRetailerID
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDResponse> getOffersByOriginRetailerSKUANDRetailerID(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetOffersByOriginRetailerSKUANDRetailerIDMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает цены по sku + store_id + tenant_id
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть цены (price):
     *        grpcurl -d '{"prices": [{"sku": ["16296"], "store_id": "1", "tenant_id": "sbermarket"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetPrices
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse> getPrices(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetPricesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает цены по retailer_sku + store_id + tenant_id
     * Обязательные параметры:
     *    retailer_sku - идентификатор ретейлера
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть цены (price):
     *        grpcurl -d '{"prices": [{"retailer_sku": "1", "store_id": "1", "tenant_id": "sbermarket"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetPricesByRetailerSKU
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKUResponse> getPricesByRetailerSKU(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKURequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetPricesByRetailerSKUMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает активность товаров по sku
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKUResponse> isActiveBySKU(
        product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKURequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getIsActiveBySKUMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает активность товаров по retailer_sku
     * Обязательные параметры:
     *    retailer_sku - идентификатор ретейлера
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKUResponse> isActiveByRetailerSKU(
        product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKURequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getIsActiveByRetailerSKUMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает активность хотя бы 1 группы по sku
     * Обязательные параметры:
     *    []sku - идентификатор ретейлера
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть активные группы по sku:
     *        grpcurl -d '{"sku": [{"sku": ["1"], "store_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.IsActiveGroupsBySKU
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKUResponse> isActiveGroupsBySKU(
        product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKURequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getIsActiveGroupsBySKUMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает группы sku по group_id
     * Обязательные параметры:
     *    retailer_id - идентификатор ретейлера
     *    group_id - идентификатор группы
     * Примеры:
     *    Вернуть группы по id:
     *        grpcurl -d '{"groups": [{"retailer_sku": "1", "group_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetGroupsByIDs
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsResponse> getGroupsByIDs(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetGroupsByIDsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает группы sku по retailer_sku
     * Обязательные параметры:
     *    retailer_id - идентификатор ретейлера
     *    retailer_sku - идентификатор sku
     * Примеры:
     *    Вернуть группы по связки входных параметров:
     *        grpcurl -d '{"groups": [{"retailer_sku": "1", "retailer_sku": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetGroupsByRetailerSKU
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKUResponse> getGroupsByRetailerSKU(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKURequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetGroupsByRetailerSKUMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает группы + описание товаров c ценой, стоком и товарным предложением по sku
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"sku": ["68496"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductGroupsWithOfferBySKU
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKUResponse> getProductGroupsWithOfferBySKU(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKURequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductGroupsWithOfferBySKUMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает группы + описание товаров c ценой, стоком и товарным предложением по retailer_sku
     * Обязательные параметры:
     *    retailer_sku - идентификатор описания ретейлера
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"retailer_sku": ["602440"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductGroupsWithOfferByRetailerSKU
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKUResponse> getProductGroupsWithOfferByRetailerSKU(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKURequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductGroupsWithOfferByRetailerSKUMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_PRODUCTS_BY_SKU = 0;
  private static final int METHODID_GET_PRODUCTS_WITH_OFFER_BY_SKU = 1;
  private static final int METHODID_GET_PRODUCTS_WITH_OFFER_BY_OFFER_IDS = 2;
  private static final int METHODID_GET_PRODUCTS_BY_EAN = 3;
  private static final int METHODID_GET_PRODUCTS_WITH_OFFER_BY_EAN = 4;
  private static final int METHODID_GET_PRODUCTS_BY_PERMALINK = 5;
  private static final int METHODID_GET_PRODUCTS_WITH_OFFER_BY_PERMALINK = 6;
  private static final int METHODID_GET_OFFERS_BY_SKUANDSTORE_ID = 7;
  private static final int METHODID_GET_STOCKS = 8;
  private static final int METHODID_GET_STOCKS_BY_RETAILER_SKU = 9;
  private static final int METHODID_GET_STOCKS_BY_OFFER_IDS = 10;
  private static final int METHODID_GET_OFFERS = 11;
  private static final int METHODID_GET_OFFERS_BY_ORIGIN_RETAILER_SKUANDRETAILER_ID = 12;
  private static final int METHODID_GET_PRICES = 13;
  private static final int METHODID_GET_PRICES_BY_RETAILER_SKU = 14;
  private static final int METHODID_IS_ACTIVE_BY_SKU = 15;
  private static final int METHODID_IS_ACTIVE_BY_RETAILER_SKU = 16;
  private static final int METHODID_IS_ACTIVE_GROUPS_BY_SKU = 17;
  private static final int METHODID_GET_GROUPS_BY_IDS = 18;
  private static final int METHODID_GET_GROUPS_BY_RETAILER_SKU = 19;
  private static final int METHODID_GET_PRODUCT_GROUPS_WITH_OFFER_BY_SKU = 20;
  private static final int METHODID_GET_PRODUCT_GROUPS_WITH_OFFER_BY_RETAILER_SKU = 21;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ProductHubFrontDataImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ProductHubFrontDataImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_PRODUCTS_BY_SKU:
          serviceImpl.getProductsBySKU((product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse>) responseObserver);
          break;
        case METHODID_GET_PRODUCTS_WITH_OFFER_BY_SKU:
          serviceImpl.getProductsWithOfferBySKU((product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse>) responseObserver);
          break;
        case METHODID_GET_PRODUCTS_WITH_OFFER_BY_OFFER_IDS:
          serviceImpl.getProductsWithOfferByOfferIDs((product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse>) responseObserver);
          break;
        case METHODID_GET_PRODUCTS_BY_EAN:
          serviceImpl.getProductsByEAN((product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse>) responseObserver);
          break;
        case METHODID_GET_PRODUCTS_WITH_OFFER_BY_EAN:
          serviceImpl.getProductsWithOfferByEAN((product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse>) responseObserver);
          break;
        case METHODID_GET_PRODUCTS_BY_PERMALINK:
          serviceImpl.getProductsByPermalink((product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse>) responseObserver);
          break;
        case METHODID_GET_PRODUCTS_WITH_OFFER_BY_PERMALINK:
          serviceImpl.getProductsWithOfferByPermalink((product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse>) responseObserver);
          break;
        case METHODID_GET_OFFERS_BY_SKUANDSTORE_ID:
          serviceImpl.getOffersBySKUANDStoreID((product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse>) responseObserver);
          break;
        case METHODID_GET_STOCKS:
          serviceImpl.getStocks((product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse>) responseObserver);
          break;
        case METHODID_GET_STOCKS_BY_RETAILER_SKU:
          serviceImpl.getStocksByRetailerSKU((product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByRetailerSKURequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse>) responseObserver);
          break;
        case METHODID_GET_STOCKS_BY_OFFER_IDS:
          serviceImpl.getStocksByOfferIDs((product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksByOfferIdsRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse>) responseObserver);
          break;
        case METHODID_GET_OFFERS:
          serviceImpl.getOffers((product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse>) responseObserver);
          break;
        case METHODID_GET_OFFERS_BY_ORIGIN_RETAILER_SKUANDRETAILER_ID:
          serviceImpl.getOffersByOriginRetailerSKUANDRetailerID((product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersByOriginRetailerSKUANDRetailerIDResponse>) responseObserver);
          break;
        case METHODID_GET_PRICES:
          serviceImpl.getPrices((product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse>) responseObserver);
          break;
        case METHODID_GET_PRICES_BY_RETAILER_SKU:
          serviceImpl.getPricesByRetailerSKU((product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKURequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesByRetailerSKUResponse>) responseObserver);
          break;
        case METHODID_IS_ACTIVE_BY_SKU:
          serviceImpl.isActiveBySKU((product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKURequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveBySKUResponse>) responseObserver);
          break;
        case METHODID_IS_ACTIVE_BY_RETAILER_SKU:
          serviceImpl.isActiveByRetailerSKU((product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKURequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveByRetailerSKUResponse>) responseObserver);
          break;
        case METHODID_IS_ACTIVE_GROUPS_BY_SKU:
          serviceImpl.isActiveGroupsBySKU((product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKURequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.IsActiveGroupsBySKUResponse>) responseObserver);
          break;
        case METHODID_GET_GROUPS_BY_IDS:
          serviceImpl.getGroupsByIDs((product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByIDsResponse>) responseObserver);
          break;
        case METHODID_GET_GROUPS_BY_RETAILER_SKU:
          serviceImpl.getGroupsByRetailerSKU((product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKURequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetGroupsByRetailerSKUResponse>) responseObserver);
          break;
        case METHODID_GET_PRODUCT_GROUPS_WITH_OFFER_BY_SKU:
          serviceImpl.getProductGroupsWithOfferBySKU((product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKURequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferBySKUResponse>) responseObserver);
          break;
        case METHODID_GET_PRODUCT_GROUPS_WITH_OFFER_BY_RETAILER_SKU:
          serviceImpl.getProductGroupsWithOfferByRetailerSKU((product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKURequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductGroupsWithOfferByRetailerSKUResponse>) responseObserver);
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

  private static abstract class ProductHubFrontDataBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ProductHubFrontDataBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return product_hub_front_data.ProductHubFrontDataOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ProductHubFrontData");
    }
  }

  private static final class ProductHubFrontDataFileDescriptorSupplier
      extends ProductHubFrontDataBaseDescriptorSupplier {
    ProductHubFrontDataFileDescriptorSupplier() {}
  }

  private static final class ProductHubFrontDataMethodDescriptorSupplier
      extends ProductHubFrontDataBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ProductHubFrontDataMethodDescriptorSupplier(String methodName) {
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
      synchronized (ProductHubFrontDataGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ProductHubFrontDataFileDescriptorSupplier())
              .addMethod(getGetProductsBySKUMethod())
              .addMethod(getGetProductsWithOfferBySKUMethod())
              .addMethod(getGetProductsWithOfferByOfferIDsMethod())
              .addMethod(getGetProductsByEANMethod())
              .addMethod(getGetProductsWithOfferByEANMethod())
              .addMethod(getGetProductsByPermalinkMethod())
              .addMethod(getGetProductsWithOfferByPermalinkMethod())
              .addMethod(getGetOffersBySKUANDStoreIDMethod())
              .addMethod(getGetStocksMethod())
              .addMethod(getGetStocksByRetailerSKUMethod())
              .addMethod(getGetStocksByOfferIDsMethod())
              .addMethod(getGetOffersMethod())
              .addMethod(getGetOffersByOriginRetailerSKUANDRetailerIDMethod())
              .addMethod(getGetPricesMethod())
              .addMethod(getGetPricesByRetailerSKUMethod())
              .addMethod(getIsActiveBySKUMethod())
              .addMethod(getIsActiveByRetailerSKUMethod())
              .addMethod(getIsActiveGroupsBySKUMethod())
              .addMethod(getGetGroupsByIDsMethod())
              .addMethod(getGetGroupsByRetailerSKUMethod())
              .addMethod(getGetProductGroupsWithOfferBySKUMethod())
              .addMethod(getGetProductGroupsWithOfferByRetailerSKUMethod())
              .build();
        }
      }
    }
    return result;
  }
}
