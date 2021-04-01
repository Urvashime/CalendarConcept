package com.test;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
//import org.testng.Assert;

public class CalendarTesting {
	public static WebDriver driver;
	public static Actions action;
	public static void main(String[] args) throws InterruptedException
	{

		
		System.setProperty("webdriver.chrome.driver", "/Users/urvashimehta/bin/chromedriver");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("https://www.freecrm.com");

		String title= driver.getTitle();
		System.out.println("title: "+title);
	    if(title.equals("#1 Free CRM customer relationship management software cloud"))
	    	System.out.println("the title matches	");
		driver.findElement(By.xpath("//a/descendant::span[contains(text(),'Log In')]")).click(); 
		driver.findElement(By.name("email")).sendKeys("uam11970@hotmail.com");
		driver.findElement(By.name("password")).sendKeys("this4CRM.");
		driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
		//driver.switchTo().frame(0);
		System.out.println("title: "+driver.getTitle());
		//Actions action = new Actions(driver);
		//WebElement elementCalendarMenu = driver.findElement(By.xpath("//*[text(),'/home']"));
				//+ "//div[@id='main-nav' and @class='ui left fixed vertical  icon menu sidebar-dark left-to-right']"));
				//"//a//descendant::span[contains(text(),'Home')]"));
		//System.out.println("Seen : "+elementCalendarMenu.isDisplayed());
		//action.moveToElement(elementCalendarMenu);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@id='main-nav']/descendant::span[contains(text(),'Calendar')]")).click();
        action = new Actions(driver);
        //move to calendar page
        action.moveToElement(driver.findElement(By.xpath("//div[@class = 'ui fluid container main-content']"))).build().perform();
        //move to actual calendar section
        action.moveToElement(driver.findElement(By.className("rbc-toolbar"))).build().perform();
        //click on todays date
        driver.findElement(By.xpath("//span[@class='today-button']")).click();
        clickOnDate("08-01-2021");
	    //driver.quit();
	}
	public static void clickOnDate(String dateToSelect)
	{
		String dateArray[] = dateToSelect.split("-");
		String dayvalue="";
		
        String month = dateArray[0];
        String day = dateArray[1];
        String year = dateArray[2];//make this same as current year later to avoid confusion
	
		boolean flag = false;//to break loop if match found for date
        int selectedMonthInt = 1;//no zero index of month to be selected
        int selectedmonthdays=0;//no of days in the to be selected month
        String[][] monthdays = {{"31","January"},{"28","February"},{"31","March"},{"30","April"},{"31","May"},{"30","June"},{"31","July"},{"31","August"},{"30","Sepetember"},{"31","October"},{"30","November"},{"31","December"}};
        String monthname ="";//nm of month to be selected
        int currentMontIndex = 0;//current month index
        int dayint = 0;//int index of day without zero
        
        
        if((month.charAt(0))== 0)
         {	
           selectedMonthInt = Integer.parseInt(month.substring(1));
           System.out.println("month: "+selectedMonthInt);
           selectedmonthdays = Integer.parseInt(monthdays[selectedMonthInt-1][0]);
           monthname = monthdays[selectedMonthInt-1][1];
           
        }  
        else
        	{
        	selectedMonthInt = Integer.parseInt(month);
        	System.out.println("month: "+selectedMonthInt);
        	selectedmonthdays = Integer.parseInt(monthdays[selectedMonthInt-1][0]);
        	monthname = monthdays[selectedMonthInt-1][1];
        	  
        	}
        //compare monthname and current month label on the site to find which button to click to move from current month
        String webMonthYearLabel=driver.findElement(By.xpath("//span[@class='rbc-toolbar-label']")).getText();
        System.out.println("A "+webMonthYearLabel);	  
        String webMonthLabel = webMonthYearLabel.substring(0,webMonthYearLabel.indexOf(' '));
        //find the displayed year label
        String webYearLabel =  webMonthYearLabel.substring(webMonthYearLabel.indexOf(' '));
        year = webYearLabel;//make the selected year as the current year
        System.out.println("Current Year:  "+webYearLabel);
       //<i aria-hidden="true" class="chevron left icon"></i>//left click for month selection
       //<i aria-hidden="true" class="chevron right icon"></i>//right click for month selection

       //<span class="rbc-toolbar-label">February 2021</span>//todays label
       	  
       //find out the indexnumber of current month
    	 for(int p=0;p<12;p++) //p=months
    	   {	
    		   
    	 	   if(webMonthLabel.equals(monthdays[p][1]))
    	 	   {
    	 		    
    	 		   currentMontIndex = p+1;//counter specifies the current month index
    	 		   //monthname = monthdays[p][1];//find which name of month, is not needed now
    	 		   break;
    	 	   }
          }
    	 System.out.println("Date to be selected : "+ dateToSelect);
    	 System.out.println("Month to be selected is : "+ selectedMonthInt);
    	 System.out.println("Current month is : "+ currentMontIndex);
    	 if(selectedMonthInt < currentMontIndex)//monthint comes before current month
    	 {
    		 //System.out.println("in: month comes before");
    		 //Show an alert that you shd not select a date from past
    		 JavascriptExecutor js = ((JavascriptExecutor) driver);
    		 js.executeScript("alert('You should not select a back date! Are you sure?');");
    		 try {
    			 Thread.sleep(2000);
    		 }catch(Exception e) {e.printStackTrace();}
    		 Alert alert = driver.switchTo().alert();
    		 alert.dismiss();
    		 //System.out.println("You should not select a back date! Are you sure?");

    		 for(int q=selectedMonthInt;q < currentMontIndex;q++) //> 
    		 { 
    			 driver.findElement(By.xpath("//div[@class='ui fluid container main-content']/descendant::button[@type='button']//i[@class='chevron left icon']")).click();
    			 try {
    				 Thread.sleep(1000);
    			 }catch(InterruptedException e) {e.printStackTrace();}
    		 }//for q
    	 }//if p
    	 //if month to be selected is later than the current month(counter)
    	 else if(selectedMonthInt > currentMontIndex)//monthint comes later than current month
    	 {
   			 System.out.println("in: month comes later");
   			
   			 //keep clicking the right icon till u reach the month needed
    		 for(int q=currentMontIndex;q < selectedMonthInt;q++) 
    		 {
    			
    		     driver.findElement(By.xpath("//div[@class='ui fluid container main-content']/descendant::button[@type='button']//i[@class='chevron right icon']")).click();
    			 try {
    			    Thread.sleep(1000);
    			 }catch(InterruptedException e) {e.printStackTrace();}
    		 }//for q 
    	 }
    	 //else if(monthint == counter)//do nothing as the month is already the one we want
    	 if((day.charAt(0))== 0)//remove zero from the date 
          {	
            dayint = Integer.parseInt(day.substring(1));
          }
    	 else 
    		 dayint = Integer.parseInt(day);
        System.out.println("date : "+dayint);
        ///html/body/div[1]/div/div[2]/div[2]/div/div[2]/div/div[2]/div/div[2]/div[  2 ]/div[2]/div/div[ 1 ]/a
        ///html/body/div[1]/div/div[2]/div[2]/div/div[2]/div/div[2]/div/div[2]/div[ 2 ]/div[2]/div/div[ 2 ]/a

        // second row
        ///html/body/div[1]/div/div[2]/div[2]/div/div[2]/div/div[2]/div/div[2]/div[   3  ]/div[2]/div/div[1]/a
        //html/body/div[1]/div/div[2]/div[2]/div/div[2]/div/div[2]/div/div[2]/div[  3    ]/div[2]/div/div[  2]/a
        //third row
        ///html/body/div[1]/div/div[2]/div[2]/div/div[2]/div/div[2]/div/div[2]/div[. 4. ]/div[2]/div/div[1]/a
        //fourth row
        ///html/body/div[1]/div/div[2]/div[2]/div/div[2]/div/div[2]/div/div[2]/div[ 5. ]/div[2]/div/div[1]/a

        action.moveToElement(driver.findElement(By.xpath("//div[@class='rbc-month-row']/descendant::div[@class='rbc-day-bg']"))).build().perform();
      
        String b4Xpath = "html/body/div[1]/div/div[2]/div[2]/div/div[2]/div/div[2]/div/div[2]/div[" ;
        String afterXpath = "]/div[2]/div/div[";
        
          if(dayint > 0 && dayint <= selectedmonthdays)	
           {
        	for(int i=0,d=2;i<6;i++,d++)//d represents current row which starts dates in row 2 
              {
        	   for(int j=1;j<=7;j++)
        	    {
        		  //System.out.println(b4Xpath+d+afterXpath+j+"]/a");
        		  String date = driver.findElement(By.xpath(b4Xpath+d+afterXpath+j+"]/a")).getText();
        		  try {
        		    Thread.sleep(2000);
        	       }catch(InterruptedException e) {e.printStackTrace();}
        		  //System.out.println("Date displayed: "+date+"   "+driver.toString());
        	      if(date.equals(day))
        	      {
        	    	//System.out.println("dateselected "+day);  
        	      	driver.findElement(By.xpath(b4Xpath+d+afterXpath+j+"]/a")).click();
        	      	flag = true;
        	      }//if
        	   
        	    }//for j
        	 
        	    if(flag)//d==selectedmonthdays || 
       		       break;
               }//for  i,d        		
  	       }//if mnthint
	       else
	          {
	    	   System.out.println("The date is not valid for the month!");
	           }//else if date invalid
        try {
		     Thread.sleep(7000);
	     }catch(InterruptedException e) {e.printStackTrace();}
		//driver.quit();
		
	}
}
