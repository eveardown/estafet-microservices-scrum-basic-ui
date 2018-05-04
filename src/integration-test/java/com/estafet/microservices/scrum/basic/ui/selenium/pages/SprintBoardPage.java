package com.estafet.microservices.scrum.basic.ui.selenium.pages;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

public class SprintBoardPage extends Page {

	@FindBy(linkText = "Projects")
	@CacheLookup
	WebElement projectsBreadcrumbLink;

	@FindBy(linkText = "Project")
	@CacheLookup
	WebElement projectBreadcrumbLink;

	@FindBy(linkText = "Sprint")
	@CacheLookup
	WebElement sprintBreadcrumbLink;

	@FindBys({
		@FindBy(xpath = "/html[1]/body[1]/div[1]/div[3]/div[1]/div[1]/div")
	})
	@CacheLookup
	List<WebElement> todoTasks;

	@FindBys({
		@FindBy(xpath = "/html[1]/body[1]/div[1]/div[4]/div[2]/div[3]/div[2]/div")
	})
	@CacheLookup
	List<WebElement> inProgressTasks;

	@FindBys({
		@FindBy(xpath = "/html[1]/body[1]/div[1]/div[4]/div[2]/div[3]/div[3]/div")
	})
	@CacheLookup
	List<WebElement> completedTasks;

	@FindBy(xpath = "/html[1]/body[1]/div[1]/div[4]/div[2]/div[1]/h2[1]/small[1]")
	@CacheLookup
	WebElement name;

	public SprintBoardPage(String projectId, String sprintId) {
		super(projectId, sprintId);
	}

	public SprintBoardPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public String uri() {
		return "/project/{1}/sprint/{2}/board";
	}

	public ProjectsPage projectsPage() {
		return click(projectsBreadcrumbLink, ProjectsPage.class);
	}

	public ProjectPage projectPage() {
		return click(projectBreadcrumbLink, ProjectPage.class);
	}

	public SprintPage sprintPage() {
		return click(sprintBreadcrumbLink, SprintPage.class);
	}

	public List<SprintBoardPageToDoTask> todoTasks() {
		return tasks(todoTasks, SprintBoardPageToDoTask.class);
	}

	public List<SprintBoardPageInProgressTask> inProgressTasks() {
		return tasks(inProgressTasks, SprintBoardPageInProgressTask.class);
	}

	public List<SprintBoardPageCompletedTask> completedTasks() {
		return tasks(completedTasks, SprintBoardPageCompletedTask.class);
	}

	private <T> List<T> tasks(List<WebElement> divs, Class<T> clazz) {
		try {
			List<T> tasks = new ArrayList<T>();
			for (WebElement div : todoTasks) {
				Constructor<T> constructor = clazz.getConstructor(WebElement.class);
				tasks.add(constructor.newInstance(div));
			}
			return tasks;
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	public String getName() {
		return name.getText();
	}

}