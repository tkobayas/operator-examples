package com.example;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;

@Version("v1")
@Group("example.example.com")
public class EchoResource extends CustomResource<EchoResourceSpec, EchoResourceStatus> implements Namespaced {}

