package com.nadisam.cruceirosvigo.domain.executor;

import com.nadisam.cruceirosvigo.domain.interactor.GetCruises;

import java.util.concurrent.Executor;

/**
 * Executor implementation can be based on different frameworks or techniques of asynchronous
 * execution, but every implementation will execute the use case
 * {@link GetCruises} out of the UI thread.
 */
public interface ThreadExecutor extends Executor {}
