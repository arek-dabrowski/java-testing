Meta:

Scenario: first scenario
Given a chrome browser
When I get to cart page
Then Cart is empty

Scenario: second scenario
Given a chrome browser
When I add t shirt and proceed
Then T shirt is in cart

Scenario: third scenario
Given a chrome browser
When I add t shirt proceed and delete
Then Cart has no items

Scenario: fourth scenario
Given a chrome browser
When I add t shirt and proceed to checkout
Then Page title is login

Scenario: fifth scenario with examples
Given a chrome browser
When I get to category <link>
Then Number of items will be <result>

Examples:
|link|result|
|http://automationpractice.com/index.php?id_category=3&controller=category|7|
|http://automationpractice.com/index.php?id_category=8&controller=category|5|
|http://automationpractice.com/index.php?id_category=5&controller=category|1|