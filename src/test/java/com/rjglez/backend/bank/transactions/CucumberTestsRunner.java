package com.rjglez.backend.bank.transactions;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:cucumber-features", plugin = {"pretty", "json:target/cucumber-report.json"})
public class CucumberTestsRunner {

}