package com.example.core.moduleManager;

import android.content.Context;

import com.google.android.play.core.splitinstall.SplitInstallException;
import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;
import com.google.android.play.core.splitinstall.SplitInstallRequest;
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener;


import java.util.Set;

public class ModuleManager {


    private Set<String> installedModules;
    private final SplitInstallManager splitInstallManager;

    public ModuleManager(Context context) {

        splitInstallManager = SplitInstallManagerFactory.create(context);
        refreshInstalledModules();
    }

    public static ModuleManager create(Context context) {

        return new ModuleManager(context);

    }


    public Set<String> refreshInstalledModules() {

        installedModules = splitInstallManager.getInstalledModules();
        return installedModules;
    }


    public boolean isDynamicModuleLoaded(String dynamicModuleName) {


        return installedModules.contains(dynamicModuleName);

    }

    public void downloadModules(Set<String> dynamicModuleNames, OnSuccessDownloadListner<Integer> listnerSucesso,
                                OnFailureDownloadListner<String> listnerFalha,
                                SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {

        // Creates a request to install a module.

        SplitInstallRequest.Builder srBuilder = SplitInstallRequest
                .newBuilder();

        // You can download multiple on demand modules per
        // request by invoking the following method for each
        // module you want to install.

        dynamicModuleNames.forEach((v) -> srBuilder.addModule(v));

        SplitInstallRequest request = srBuilder.build();


        splitInstallManager.registerListener(splitInstallStateUpdatedListener);

        splitInstallManager
                // Submits the request to install the module through the
                // asynchronous startInstall() task. Your app needs to be
                // in the foreground to submit the request.
                .startInstall(request)
                // You should also be able to gracefully handle
                // request state changes and errors. To learn more, go to
                // the section about how to Monitor the request state.
                .addOnSuccessListener(sessionId -> {
                    listnerSucesso.onSuccess(sessionId);
                })
                .addOnFailureListener(exception -> {
                    listnerFalha.onFailure(exception.getMessage());
                });

    }
}
