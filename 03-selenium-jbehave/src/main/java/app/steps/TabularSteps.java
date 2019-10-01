package app.steps;

import java.util.ArrayList;
import java.util.Map;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.model.ExamplesTable;
import testJBehave.Power;


public class TabularSteps{
	
	ExamplesTable table;
	ArrayList<String> numbers;
	Power power;
	@Given("the numbers: $ranksTable")
	public void givenTheNumbersnumber(ExamplesTable ranksTable){
		  this.table = ranksTable;
		  this.numbers = toNumbers(ranksTable);
		  System.out.println("Numbers are: " + numbers);
	}
	
	private ArrayList<String> toNumbers(ExamplesTable table){
		ArrayList<String> result = new ArrayList<String>();
		for (Map<String, String> row: table.getRows()){
			result.add(row.get("left"));
			result.add(row.get("right"));
		}
		return result;
	}
	
	@Then("powers are: $table")
	public void thenPowersAndSizeIssizepowers(ExamplesTable table){

	}
	
}