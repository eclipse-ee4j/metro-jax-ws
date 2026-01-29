# Eclipse Metro greets Helidon

Eclipse Metro - Helidon integration.

## helidon-soap & helidon-soap-mp

An integration layer for Helidon SE & MP

### `MetroSupport` config options

Following shows default settings:

```
metro:
  catalog: metro-catalog.xml
  descriptor: sun-jaxws.xml
# dump can be all|none|server|client
  dump: none
  dump-treshold: 4096
  status-page: true
# not fully supported yet:
#  web-context: metro
#  wsdl-root: WEB-INF/wsdl
```

## samples

Contains set of samples for supported Eclipse Metro version.

### helidon-soap-demo(-mp)

A demo contains 4 services and tests which execute each of them.

Requires Java SE 11+.

#### Listing all available Jakarta XML Web Services

Visit: `http://127.0.0.1:8080/metro/`. This functionality can be disabled
by `MetroSupport.builder().publishStatusPage(false).build()`.

#### Checking out request/response messages

`MetroSupport.builder().dumpService(true).dumpClient(true).build()`.

Dump treshold (default `4096`) can be changed by calling `dumpTreshold(8192)` on the builder.

#### Jakarta XML Web Services running on Helidon web server

`org.eclipse.metro.helidon.example.ws.SoapWs` (famous _Hello world_ example) is made available on http://127.0.0.1:8080/metro/SoapWsService,
its description is available at http://127.0.0.1:8080/metro/SoapWsService?wsdl

`org.eclipse.metro.helidon.example.addressing.AddressingWS` uses WS-Addressing and shows injection of
an instance of `WebServiceContext` into the web service. It is available on http://127.0.0.1:8080/metro/AddressingWSService,
its description is available at http://127.0.0.1:8080/metro/AddressingWSService?wsdl

#### Jakarta XML Web Service from WSDL running on Helidon web server

`org.eclipse.metro.helidon.example.fromwsdl.AddNumbersImpl` is made available on http://127.0.0.1:8080/metro/addnumbers,
its description is available at http://127.0.0.1:8080/metro/addnumbers?wsdl

#### Rest service calling remote SOAP service

`org.eclipse.metro.helidon.example.client.RestService` Helidon service is a `text/plain` frontend to remote
SOAP Web Service (https://www.dataaccess.com/webservicesserver/NumberConversion.wso) and offers 2 operations:
* http://127.0.0.1:8080/rest/toDollars?number=777
* http://127.0.0.1:8080/rest/toWords?number=888


### metro-samples

Few existing sample projects from [JAX-WS RI](https://github.com/eclipse-ee4j/metro-jax-ws/tree/master/jaxws-ri/docs/samples/src/main/samples)
migrated to Helidon.
