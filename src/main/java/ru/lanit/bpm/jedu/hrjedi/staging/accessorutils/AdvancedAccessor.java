/*
 * Copyright (c) 2008-2021
 * LANIT
 * All rights reserved.
 *
 * This product and related documentation are protected by copyright and
 * distributed under licenses restricting its use, copying, distribution, and
 * decompilation. No part of this product or related documentation may be
 * reproduced in any form by any means without prior written authorization of
 * LANIT and its licensors, if any.
 *
 * $Id$
 */
package ru.lanit.bpm.jedu.hrjedi.staging.accessorutils;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.util.Assert;

public abstract class AdvancedAccessor {

    @SuppressWarnings("Duplicates")
    protected void mapToTask(DelegateTask task, String... taskAndProcVars) {
        map(task, (taskVars, processVars) -> {
            Assert.isTrue(taskAndProcVars.length % 2 == 0, "vars should by even array of dst = src var names");
            for (int i = 0; i < taskAndProcVars.length; i += 2) {
                String taskVar = taskAndProcVars[i];
                String processVar = taskAndProcVars[i + 1];
                taskVars.set(taskVar, processVars.get(processVar));
            }
        });
    }

    @SuppressWarnings("Duplicates")
    protected void mapFromTask(DelegateTask task, String... procAndTaskVars) {
        map(task, (taskVars, processVars) -> {
            Assert.isTrue(procAndTaskVars.length % 2 == 0, "vars should by even array of dst = src var names");
            for (int i = 0; i < procAndTaskVars.length; i += 2) {
                String processVar = procAndTaskVars[i];
                String taskVar = procAndTaskVars[i + 1];
                processVars.set(processVar, taskVars.get(taskVar));
            }
        });
    }

    protected void map(DelegateTask task, Mapping mapping) {
        mapping.map(new TaskVariables(task), new ProcessVariables(task));
    }

    @SuppressWarnings("unchecked")
    protected <T> T getTaskVariable(DelegateTask task, String variableName) {
        return (T) task.getVariableLocal(variableName);
    }

    protected void setTaskVariable(DelegateTask task, String variableName, Object value) {
        task.setVariableLocal(variableName, value);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getExecutionVariableLocal(DelegateTask task, String variableName) {
        return (T) task.getExecution().getVariableLocal(variableName);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getProcessVariable(DelegateTask task, String variableName) {
        return (T) task.getExecution().getProcessInstance().getVariableLocal(variableName);
    }

    protected void setProcessVariable(DelegateTask task, String variableName, Object value) {
        task.getExecution().getProcessInstance().setVariableLocal(variableName, value);
    }

    @FunctionalInterface
    protected interface Mapping {
        void map(Variables taskVars, Variables processVars);
    }

    protected interface Variables {
        Object get(String name);

        void set(String name, Object value);
    }

    protected static class TaskVariables implements Variables {
        private final DelegateTask task;

        public TaskVariables(DelegateTask task) {
            this.task = task;
        }

        @Override
        public Object get(String name) {
            return task.getVariableLocal(name);
        }

        @Override
        public void set(String name, Object value) {
            task.setVariableLocal(name, value);
        }
    }

    protected static class ProcessVariables implements Variables {
        private final DelegateExecution process;

        public ProcessVariables(DelegateTask task) {
            process = task.getExecution().getProcessInstance();
        }

        @Override
        public Object get(String name) {
            return process.getVariableLocal(name);
        }

        @Override
        public void set(String name, Object value) {
            process.setVariableLocal(name, value);
        }
    }
}
