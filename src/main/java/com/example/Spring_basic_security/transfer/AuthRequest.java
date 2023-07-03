package com.example.Spring_basic_security.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/*
In a Spring Boot application, the DTO (Data Transfer Object) layer is a separate layer that is used for transferring
data between different parts of the application, typically between the controller and service layers. While the model
layer represents the domain objects/entities and their behavior, the DTO layer serves as an intermediary for
transferring data across different layers or components of the application.

The purpose of the DTO layer is to encapsulate the data that needs to be transferred, providing a clean and standardized
interface for communication. It helps in decoupling the internal representation of data used within the application from
the external representation exposed through APIs or other interfaces.

Here are a few reasons why a separate DTO layer is beneficial:

1. Data Transformation: DTOs allow you to transform data from the internal representation (domain objects/entities) to
a representation suitable for external use, such as RESTful APIs. They provide a controlled way to shape the data and
include or exclude specific fields as required.

2. Data Transfer Efficiency: DTOs can be optimized for efficient data transfer by including only the necessary data and
 avoiding unnecessary associations or dependencies. This can help reduce network bandwidth and improve overall
 performance.

3. Encapsulation: DTOs provide a clear boundary for data exchange and can help maintain encapsulation between layers.
They prevent leaking of domain-specific details to the external interfaces and provide better control over the data
 being exposed.

4. Versioning and Compatibility: DTOs can aid in managing versioning and backward compatibility of APIs. By decoupling
 the external representation from the internal model, you have the flexibility to evolve the internal model without
 impacting existing clients.

While the model layer represents the domain objects/entities and their behavior, it may not always be suitable for
direct use in communication with external components. The DTO layer bridges this gap by providing a tailored
representation of data specifically designed for efficient and controlled data transfer.

It's worth noting that the usage of a DTO layer is not mandatory for every application. For simpler applications, the
model layer itself might suffice for data transfer. However, as applications grow in complexity or require fine-grained
 control over data transfer, having a separate DTO layer can provide significant benefits in terms of maintainability,
 performance, and flexibility.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    private String userName;
    private String password;
//    private  List<String> authorities;


}
