# ecommerce-assessment
## CQRS
![image](https://github.com/MohammedHydr/ecommerce-assessment/assets/93540731/c34cf453-24c1-47a1-83f7-eb3822a8caa6)

## Write Database(Postgres)

![image](https://github.com/MohammedHydr/ecommerce-assessment/assets/93540731/00e883d5-33f4-4d72-ac71-46eefc7de24d)

### Customer
1. customerId: Auto-generated unique identifier for each customer.
2. name: Stores the name of the customer.
3. email: Stores the email of the customer.
4. phoneNumber: Stores the phone number of the customer with a maximum length of 15.
The Customer entity focuses on storing information that can identify and contact the customer. Each customer has a unique customerId, serving as a primary key and assisting in referencing orders related to them.
### Order
1. orderId: Auto-generated unique identifier for each order.
2. orderDate: Timestamp marking the date when the order was placed will be generated automatically when the order is created.
3. customer: A many-to-one relationship with the Customer entity.
4. orderItems: A one-to-many relationship with OrderItem.
5. totalPrice: Stores the total price of all items in the order, defined as a decimal with two decimal places and also it will be calculated based on the quantity and the products prices.
The Order entity establishes a many-to-one relationship with the Customer entity. It also contains multiple OrderItems, justifying a one-to-many relationship with the OrderItem entity. The totalPrice is calculated server-side before being persisted, thereby ensuring data integrity.
### OrderItem
1. itemId: Auto-generated unique identifier for each item in an order.
2. order: Many-to-one relationship with Order.
3. product: Many-to-one relationship with Product.
4. quantity: Number of units of the product.
5. price: The price at which the product was sold, preserved for historical accuracy will be taken from the product.
### Product
1. productId: Auto-generated unique identifier for each product.
2. name: Name of the product.
3. price: Price of the product, stored as a decimal.
4. stock: Number of items available in stock.
5. stockStatus: A boolean that indicates whether the item is in stock or not will be automatically updated based on the stock.

### Relationships and Normalization
* Customer to Order: One-to-Many
* A single customer can place multiple orders, while each order is associated with only one customer.
* Order to OrderItem: One-to-Many
* An order can contain multiple items, but each item belongs to only one order.
* Product to OrderItem: Many-to-One
* A single product can be part of multiple order items across different orders, but each order item is associated with only one product.
* The schema design aims for Third Normal Form (3NF), ensuring that all data is factually dependent only on the primary key. This has several benefits:
  * Elimination of Duplicate Data: Ensuring that each piece of data is stored only once, thereby reducing storage costs and improving data integrity.
  * Data Integrity: The use of foreign keys and relationships ensures that the data is consistent and reliable.
  * Logical Data Storage: Each piece of information is stored in its most logical place. For example, price is stored in OrderItem to maintain historical data even if the current price of the product changes.

## Read Database (MongoDb)

## Admin RestApi

## 
