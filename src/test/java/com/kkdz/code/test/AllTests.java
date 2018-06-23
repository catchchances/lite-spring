package com.kkdz.code.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.kkdz.code.test.v1.V1AllTests;
import com.kkdz.code.test.v2.V2AllTests;

@RunWith(Suite.class)
@SuiteClasses({ V1AllTests.class, V2AllTests.class })
public class AllTests {

}
