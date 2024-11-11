package ru.lanit.bpm.jedu.hrjedi.app.impl.vacation;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class FindVacataionsToApproveTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private FindVacationsToApproveUseCase findVacationsToApproveUseCase;

    @Test
    public void findVacationsToApprove_tasksFound() {
        String approverLogin = "tester";

        Task task1 = Mockito.mock(Task.class);
        Task task2 = Mockito.mock(Task.class);

        TaskQuery taskQuery = Mockito.mock(TaskQuery.class);

        Mockito.when(task1.getProcessInstanceId()).thenReturn("id1");
        Mockito.when(task2.getProcessInstanceId()).thenReturn("id2");

        List<Task> tasks = List.of(task1, task2);

        Mockito.when(taskService.createTaskQuery()).thenReturn(taskQuery);
        Mockito.when(taskQuery.processVariableValueEquals("approverLogin", approverLogin)).thenReturn(taskQuery);
        Mockito.when(taskQuery.active()).thenReturn(taskQuery);
        Mockito.when(taskQuery.list()).thenReturn(tasks);

        Set<String> results = findVacationsToApproveUseCase.execute(approverLogin);

        Assert.assertEquals(Set.of("id1", "id2"), results);
    }

    @Test
    public void findVacationsToApprove_noTasks() {
        String approverLogin = "tester";

        TaskQuery taskQuery = Mockito.mock(TaskQuery.class);

        Mockito.when(taskService.createTaskQuery()).thenReturn(taskQuery);
        Mockito.when(taskQuery.processVariableValueEquals("approverLogin", approverLogin)).thenReturn(taskQuery);
        Mockito.when(taskQuery.active()).thenReturn(taskQuery);
        Mockito.when(taskQuery.list()).thenReturn(Collections.emptyList());

        Set<String> results = findVacationsToApproveUseCase.execute(approverLogin);

        Assert.assertEquals(Collections.emptySet(), results);
    }
}
