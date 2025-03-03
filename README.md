# Welcome. This portfolio showcases my capstone project for CS-499 and my education journey at Southern New Hampshire University.

## Professional Self-Assesment

#### During my time in the computer science program at SNHU, I have had the oppurtunity to develop my professional skills and abilities. I started the program with minimal experience in computer science and code development. As my time in the program passed, I quickly realized I had made a great choice in this field of study. The deeper I went into the program, the more I wanted to learn.

#### The process of enhancing this capstone project has taught me the power of making a plan before beginning to work, along with the power of iterative development. I was originally taught these principles when I began my time in the computer science program. I understood them to some degree, but as I kept learning and enhancing my skills, it became clear to me why it is best to develop in this way. I have been able to improve the quality of my work by planning ahead what I want to accomplish. Developing my code in iterations allows me to tackle large problems by breaking them up into smaller pieces. The combination of these skills has led me to become a better developer.

#### There are certain vital skills I have learned such as: collaborating in a team environment, communicating with stakeholders, data structures and algorithms, software engineering and databases, and secure coding. Each course I have taken refined one or more skills in unique ways, leading up to my final capstone project presented in this portfolio.

## Skills learned from courses
 - [Writing test code](https://github.com/BRCooperrider/CS-320)
 - [OpenGL basics](https://github.com/BRCooperrider/CS-330)
 - [CRUD](https://github.com/BRCooperrider/CS-340)
 - [Embedded programming](https://github.com/BRCooperrider/CS-350)
 - [Mobile development](https://github.com/BRCooperrider/CS-360)
 - [Machine learning](https://github.com/BRCooperrider/CS-370)
 - [Secure coding principles](https://github.com/BRCooperrider/CS-405)

## Evaluation of Capstone
- Employ strategies for building collaborative environments that enable diverse audiences to support organizational decision making in the field of computer science
- Design, develop, and deliver professional-quality oral, written, and visual communications that are coherent, technically sound, and appropriately adapted to specific audiences and contexts
- Design and evaluate computing solutions that solve a given problem using algorithmic principles and computer science practices and standards appropriate to its solution, while managing the trade-offs involved in design choices
- Demonstrate an ability to use well-founded and innovative techniques, skills, and tools in computing practices for the purpose of implementing computer solutions that deliver value and accomplish industry-specific goals
- Develop a security mindset that anticipates adversarial exploits in software architecture and designs to expose potential vulnerabilities, mitigate design flaws, and ensure privacy and enhanced security of data and resources

## Mobile inventory application

#### For this capstone, I had to choose one or more artifacts that demonstrate my skills in three key categories:
- Software design and engineering
- Algorithms and data structure
- Databases

#### While I debated improving different artifacts for each category, I decided to improve a single artifact instead. It is a mobile inventory application I made during my time in CS-360: Mobile Architecture and Programming. This project was big enough where I could demonstrate my skills in the three categories using a single project.

#### The original program was my first introduction using Android Studio. I enjoyed using it due to its straightforwardness and ability to emulate mobile devices in real time. The inventory application had a few requirements. It needed to contain a login page, contain SMS capabilities, utilize separate databases, and provide basic funtions to add and remove items from one of the databases. As expected, the original program took a lot of time to develop due to my unfamilarity with Android studio and experience developing on a larger scale. By the time I got around to making the enhancements for this capstone I felt much better in my ability to improve upon it.

#### [Code review](https://www.youtube.com/watch?v=urmln6oFHZg) of the original artifact.

#### Link to the [Original](https://github.com/BRCooperrider/BRCooperrider.github.io/tree/main/Inventory_App_Iterations/Original/Project_2_Bryce_Cooperrider_Inventory_App) app I improved upon.

## Enhancement One: Software Design and Engineering

#### Link to [Enhancement One](https://github.com/BRCooperrider/BRCooperrider.github.io/tree/main/Inventory_App_Iterations/Original/Project_2_Bryce_Cooperrider_Inventory_App)
#### Link to [Enhancement One Writeup](https://github.com/BRCooperrider/BRCooperrider.github.io/blob/main/Writeups/Enhancement%20One%20Bryce%20Cooperrider.pdf)

#### Images
- [Enhanced Inventory Page](https://github.com/BRCooperrider/BRCooperrider.github.io/blob/main/Images/Enhancement%20One/InventoryScreen_EnhancedLayout.PNG)
- [Item Info Popup](https://github.com/BRCooperrider/BRCooperrider.github.io/blob/main/Images/Enhancement%20One/InventoryScreen_ItemInfo.PNG)

#### To start with Enhancement One, I wanted to clean up the layout of the main inventory screen. I removed a row from the top and bottom of the recycler view. This allows room for the floating action button at the bottom and filtering/sorting capabilities at the top. I also added a popup to each item in the inventory to view additional details about the selected item. Within this popup, there is an additional way to remove an item from the database; just click the delete item button and it is removed. 

#### Reflecting on this enahncement, the first thing I learned was additional functionality of XML files. I am not the best at UI design so I wasn’t sure what I could modify within the files to make the layout look better. I have knowledge of more ways to modify these files now. I also learned how to incorporate popup views within an application. 
#### The biggest challenge I faced was getting the recycler view to update when an item was deleted from it. I knew the database was reflecting the change from the confirmation message, but it wasn’t being visually reflected. Eventually I learned that it was due to the cursor not properly being updated, so I added additional code to have it work properly.

#### The completion of this enhancement means I can check off two of the capstone evaluation criteria.
- [x] Employ strategies for building collaborative environments that enable diverse audiences to support organizational decision making in the field of computer science
- [x] Design, develop, and deliver professional-quality oral, written, and visual communications that are coherent, technically sound, and appropriately adapted to specific audiences and contexts
- [ ] Design and evaluate computing solutions that solve a given problem using algorithmic principles and computer science practices and standards appropriate to its solution, while managing the trade-offs involved in design choices
- [ ] Demonstrate an ability to use well-founded and innovative techniques, skills, and tools in computing practices for the purpose of implementing computer solutions that deliver value and accomplish industry-specific goals
- [ ] Develop a security mindset that anticipates adversarial exploits in software architecture and designs to expose potential vulnerabilities, mitigate design flaws, and ensure privacy and enhanced security of data and resources

## Enhancement Two: Algorithms and Data Structures

#### Link to [Enhancement Two](https://github.com/BRCooperrider/BRCooperrider.github.io/tree/main/Inventory_App_Iterations/Enhancement_Two/Project_2_Bryce_Cooperrider_Inventory_App)
#### Link to [Enhancement Two Writeup](https://github.com/BRCooperrider/BRCooperrider.github.io/blob/main/Writeups/Enhancement%20Two%20Bryce%20Cooperrider.pdf)

#### Images
- [Sort and Filter Buttons](https://github.com/BRCooperrider/BRCooperrider.github.io/blob/main/Images/Enhancement%20Two/Enhancement_Two_MainScreen.PNG)
- [Sorting Options](https://github.com/BRCooperrider/BRCooperrider.github.io/blob/main/Images/Enhancement%20Two/Enhancement_Two_SortFunction_Main.PNG)
- [Sort by Name Ascending](https://github.com/BRCooperrider/BRCooperrider.github.io/blob/main/Images/Enhancement%20Two/Enhancement_Two_SortFunction_NameAsc.PNG)
- [Sort by Name Descending](https://github.com/BRCooperrider/BRCooperrider.github.io/blob/main/Images/Enhancement%20Two/Enhancement_Two_SortFunction_NameDesc.PNG)
- [Sort by Quantity Ascending](https://github.com/BRCooperrider/BRCooperrider.github.io/blob/main/Images/Enhancement%20Two/Enhancement_Two_SortFunction_QuantityAsc.PNG)
- [Sort by Quantity Descending](https://github.com/BRCooperrider/BRCooperrider.github.io/blob/main/Images/Enhancement%20Two/Enhancement_Two_SortFunction_QuantityDesc.PNG)
- [Filter Popup](https://github.com/BRCooperrider/BRCooperrider.github.io/blob/main/Images/Enhancement%20Two/Enhancement_Two_FilterMain.PNG)
- [Filter Example](https://github.com/BRCooperrider/BRCooperrider.github.io/blob/main/Images/Enhancement%20Two/Enhancement_Two_FilterExample.PNG)

#### Enhancement Two showcases my ability to add algortithms and data structures. I added multiple sorting options and a filter function to help the user better understand the contents of their inventory. I originally wanted to add a 'search' function as well, but couldn't distinguish the feature enough from the filter function. I ended up scrapping the idea because the filter is able to find partial strings as well as full items.

#### Reflecting on Enhancement Two, I learned was more about the the use of popups within Android Studio. I chose to use them because they give a clean and responsive feel when pulling up a function, without needing to load a whole different page. I also learned much more about the setOnClickListener() function within Android Studio as I make great use of this through all the buttons and functionality I have added. 
#### One challenge I started to face was my emulator not loading while working on these enhancements. I’m unsure why this was the case as no other code I’ve added caused it to hang and stall out. I fixed this by downloading a different phone to emulate, as well as research common problems that may cause this issue. I’m still not exactly sure what caused it but emulating a fresh device worked well for me. 
#### Another Challenge I faced is with the Filter button. Once an item is filtered, it shows the appropriate results as expected. From there, trying to change the quantity of an item in that view will work for one click, but then proceed to go back to the default view of the inventory. I managed to fix this by keeping the filtered view open until the filter button is clicked again.


#### The completion of this enhancement checks off two more of the capstone evaluation criteria.
- [x] Employ strategies for building collaborative environments that enable diverse audiences to support organizational decision making in the field of computer science
- [x] Design, develop, and deliver professional-quality oral, written, and visual communications that are coherent, technically sound, and appropriately adapted to specific audiences and contexts
- [x] Design and evaluate computing solutions that solve a given problem using algorithmic principles and computer science practices and standards appropriate to its solution, while managing the trade-offs involved in design choices
- [x] Demonstrate an ability to use well-founded and innovative techniques, skills, and tools in computing practices for the purpose of implementing computer solutions that deliver value and accomplish industry-specific goals
- [ ] Develop a security mindset that anticipates adversarial exploits in software architecture and designs to expose potential vulnerabilities, mitigate design flaws, and ensure privacy and enhanced security of data and resources

## Enhancement Three: Databases

#### Link to [Enhancement Three](https://github.com/BRCooperrider/BRCooperrider.github.io/tree/main/Inventory_App_Iterations/Enhancement_Three/Project_2_Bryce_Cooperrider_Inventory_App)
#### Link to [Enhancement Three Writeup](https://github.com/BRCooperrider/BRCooperrider.github.io/blob/main/Writeups/Enhancement%20Three%20Bryce%20Cooperrider.pdf)

#### Images
- [Enhanced Login Page](https://github.com/BRCooperrider/BRCooperrider.github.io/blob/main/Images/Enhancement%20Three/LoginPage_Enhanced.PNG)

#### Enhancement Three showcases my ability to work with databases. I started by adding an extra column to the inventory database allowing for a description of each item. This can help the user add additional details to their items if they wish. Next, I improved upon the user database where login and password information is stored. I added input validation for the email address to ensure entries follow a typical email address, as well as added security to the stored passwords by utilizing SHA-256 encryption.

#### Reflecting on the final enhancement, I learned more about UI design for designing the login page, and more importantly how to hash passwords using encryption. I remember the theory behind it from a previous class, but couldn’t quite remember how to implement it, especially in Android Studio. 
#### I struggled a bit at first incorporating it but got it working properly through trial and error. I also struggled thinking of a way to ensure an email address gets entered into the username line. I dug around online to find ways to handle this and found out about an external library through Apache commons, and how they have an email validator. After looking at how to implement it, it works great. I haven’t tested fringe cases but out of the scenarios I have tried, I need to add the @ symbol, as well as a domain extension like .com to get the information to be stored in the user database, which works exactly as I want it to. Improvements to the login page weren’t difficult, just time consuming as I adjusted constraints in the way I wanted them to look.

#### The completion of this last enhancement checks off the last capstone evaluation criteria.
- [x] Employ strategies for building collaborative environments that enable diverse audiences to support organizational decision making in the field of computer science
- [x] Design, develop, and deliver professional-quality oral, written, and visual communications that are coherent, technically sound, and appropriately adapted to specific audiences and contexts
- [x] Design and evaluate computing solutions that solve a given problem using algorithmic principles and computer science practices and standards appropriate to its solution, while managing the trade-offs involved in design choices
- [x] Demonstrate an ability to use well-founded and innovative techniques, skills, and tools in computing practices for the purpose of implementing computer solutions that deliver value and accomplish industry-specific goals
- [x] Develop a security mindset that anticipates adversarial exploits in software architecture and designs to expose potential vulnerabilities, mitigate design flaws, and ensure privacy and enhanced security of data and resources

## Conclusion

#### I am thankful to have had the opportunity to showcase my skills learned through SNHU's computer science program.
#### It has provided me with necessary skills such as 
- [Collaborating in a team environment](https://github.com/BRCooperrider/CS_255) where I worked with peers to analyze the needs of the client DriverPass
- [Communicate with stakeholders](https://github.com/BRCooperrider/CS_305) figuring out ways to fix security vulnerabilities
- Experience with [Data structures and algorithms](https://github.com/BRCooperrider/CS-370) learning about Q-training and its efficiencies, as well as general knowledge of [data structrues](https://github.com/BRCooperrider/CS_300) and when to use them
- [Database knowledge](https://github.com/BRCooperrider/CS-340) learning CRUD principles and applications
- [Functional and secure coding](https://github.com/BRCooperrider/CS-320) to understand the importance of secure code

#### The culmination of my studies has set me up to be confident in my future endeavors for a career utilizing computer science. This capstone provides an excellent baseline of my work which will continuously get better as I gain more experience within the field.

#### Here is the link to my final iteration of the [Inventory App](https://github.com/BRCooperrider/BRCooperrider.github.io/tree/main/Inventory_App_Iterations/Enhancement_Final/Project_2_Bryce_Cooperrider_Inventory_App)