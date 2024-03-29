package com.example;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoResourceReconciler implements Reconciler<EchoResource> {

  private static final Logger log = LoggerFactory.getLogger(EchoResourceReconciler.class);

  private final KubernetesClient client;

  public EchoResourceReconciler(KubernetesClient client) {
    this.client = client;
  }

  // TODO Fill in the rest of the reconciler

  @Override
  public UpdateControl<EchoResource> reconcile(EchoResource resource, Context context) {
    log.info("This is the reconciler of the echo-operator. resource message is {}", resource.getSpec().getInputMessage());
    if (reconcileStatus(resource,context)){
      return UpdateControl.updateStatus(resource);
    }
    return UpdateControl.noUpdate();
  }

  private boolean reconcileStatus(EchoResource resource, Context<EchoResource> context) {
    String desiredMsg = resource.getSpec().getInputMessage();
    if (resource.getStatus() == null){
      // initialize if needed
      resource.setStatus(new EchoResourceStatus());
      resource.getStatus().setEchoMessage("");
    }
    if (!resource.getStatus().getEchoMessage().equalsIgnoreCase(desiredMsg)){
      // the status needs to be updated with a new echo message
      resource.getStatus().setEchoMessage(desiredMsg);
      log.info("Setting echo resource status message to {}", desiredMsg);
      // return true to signal the need to update status in Kubernetes
      return true;
    }
    return false;
  }
}

