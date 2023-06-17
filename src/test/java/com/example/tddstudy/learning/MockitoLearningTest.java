package com.example.tddstudy;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class LearningTest {

    @Test
    public void whenThenReturnTest(){
        List<String> mocks = mock(ArrayList.class);

        String givenValue = mocks.get(0);

        when(givenValue).thenReturn("ksb");

        assertEquals("ksb", mocks.get(0));
    }

    @Test
    public void anyTest(){
        List<String> mocks = mock(ArrayList.class);

        String givenValue = mocks.get(anyInt());

        when(givenValue).thenReturn("ksb");

        assertEquals("ksb", mocks.get(0));
        assertEquals("ksb", mocks.get(1));
        assertEquals("ksb", mocks.get(10_000_000));
    }

    @Test
    public void verifyTest(){
        List<String> mocks = mock(ArrayList.class);

        mocks.add("ksb");

        verify(mocks).add("ksb");
    }

    @Test
    public void verifyTimeTest(){
        List<String> mocks = mock(ArrayList.class);

        mocks.add("ksb");
        mocks.add("ksb2");

        verify(mocks, times(2)).add(anyString());
    }

    @Test
    public void verifyAtLeastAndAtLeastOnceTest(){
        List<String> mocks = mock(ArrayList.class);

        mocks.add("ksb");
        mocks.add("ksb2");
        mocks.add("ksb3");
        mocks.add("ksb4");

        verify(mocks, atLeast(2)).add(anyString());
        verify(mocks, atLeastOnce()).add(anyString());
    }

    @Test
    public void argumentCaptorTest(){
        List<String> mocks = mock(ArrayList.class);
        ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);

        mocks.add("ksb");
        verify(mocks).add(arg.capture());

        assertEquals("ksb", arg.getValue());
    }

    @Test
    public void spyingEqualTest(){
        List<String> spies = spy(ArrayList.class);

        spies.add("ksb");
        assertEquals("ksb", spies.get(0));
    }

    @Test
    public void spyingVerifyTest(){
        List<String> spies = spy(ArrayList.class);

        spies.add("ksb");
        verify(spies).add("ksb");

        spies.get(0);
        verify(spies).get(0);
    }

    @Test
    public void spyingIndexOutOfBoundsExceptionTest(){
        List<String> spies = spy(ArrayList.class);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            when(spies.get(0)).thenReturn("ksb");
        });
    }

    @Test
    public void spyingDoReturnTest(){
        List<String> spies = spy(ArrayList.class);

        doReturn("ksb").when(spies).get(0);
        doReturn("ksb1").when(spies).get(1);

        assertEquals("ksb", spies.get(0));
        assertEquals("ksb1", spies.get(1));
    }

    @Test
    public void whenThenThrowTest(){
        List<String> mocks = mock(ArrayList.class);

        mocks.add(null);

        when(mocks.get(0))
                .thenThrow(NullPointerException.class);
    }
}
