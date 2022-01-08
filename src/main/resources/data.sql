insert into user (id, email_address , first_name , last_name , role , ssn , user_name ) 
values (100001,'savanchi@exmaple.com','Sriharsha', 'Avanchi', 'admin', '401318448', 'savanchi');

insert into user (id, email_address , first_name , last_name , role , ssn , user_name ) 
values(100002,'gwiser@exmaple.com','Greg', 'wiser', 'admin', '70906649', 'gwiser');

insert into user (id, email_address , first_name , last_name , role , ssn , user_name ) 
values(100003,'dmark@exmaple.com','David', 'Mark', 'admin', '16325404', 'dmark');

insert into user (id, email_address , first_name , last_name , role , ssn , user_name ) 
values(100004,'kwalbert@exmaple.com','Kevin', 'Walbert', 'admin', '790714615', 'kwalbert');

insert into user (id, email_address , first_name , last_name , role , ssn , user_name ) 
values(100005,'mzuper@exmaple.com','Mike', 'Zuper', 'admin', '149-13-7317', 'mzuper');

insert into user (id, email_address , first_name , last_name , role , ssn , user_name ) 
values(100006,'kmiller@exmaple.com','Ken', 'Miller', 'admin', '003 06 8815', 'kmiller');

insert into user (id, email_address , first_name , last_name , role , ssn , user_name ) 
values(100007,'pjohn@exmaple.com','Peter', 'John', 'admin', '805 14 1893', 'pjohn');


insert into orders(order_id, order_desc, user_id) values(200001, 'order11', 100001);        
insert into orders(order_id, order_desc, user_id) values(200002, 'order12', 100001);
insert into orders(order_id, order_desc, user_id) values(200003, 'order13', 100001);
insert into orders(order_id, order_desc, user_id) values(200004, 'order21', 100002);
insert into orders(order_id, order_desc, user_id) values(200005, 'order22', 100002);
insert into orders(order_id, order_desc, user_id) values(200006, 'order31', 100003);
insert into orders(order_id, order_desc, user_id) values(200007, 'order32', 100004);
insert into orders(order_id, order_desc, user_id) values(200008, 'order33', 100004);
insert into orders(order_id, order_desc, user_id) values(200009, 'order34', 100005);
insert into orders(order_id, order_desc, user_id) values(2000010, 'order35', 100005);
insert into orders(order_id, order_desc, user_id) values(2000011, 'order36', 100006);
insert into orders(order_id, order_desc, user_id) values(2000012, 'order37', 100007);


insert into api_users(id,api_user_name,api_user_password)values(300001,'emapuser', 'Epam$123');
