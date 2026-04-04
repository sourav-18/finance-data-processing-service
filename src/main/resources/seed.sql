INSERT INTO users (name, email, password, role, status)
SELECT 'admin', 'admin@gmail.com', '$2a$10$dUTe8dAzpIk0hOcwhEj7zO7gnsewGsC2mFSS3Enahbbr8E.K/ooYa',
'Admin','Active'WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = 1);

INSERT INTO users (name, email, password, role, status,created_by)
SELECT 'analyst', 'analyst@gmail.com','$2a$10$GybehdGNAJ18SCSY0rw1xet6qleN2an3gmEA9Jnjgv/RmJNZVWRWa',
'Analyst', 'Active',1 WHERE NOT EXISTS (  SELECT 1 FROM users WHERE id = 2);

INSERT INTO users ( name, email, password, role, status,created_by)
SELECT 'viewer','viewer@gmail.com','$2a$10$FGNpFH.ecsrtvgkSxkjKqeNP9xaDOlyjUj5.yl9N4.EVdJidch6Tm',
'Viewer','Active',1 WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = 3);

INSERT INTO finances (created_at, updated_at, amount, category, note, status, type, created_by, updated_by, is_deleted)
SELECT '2026-01-15 09:15:10','2026-01-15 09:15:10',5000,'Salary','Monthly salary credited','Success','Income',1,NULL,false
WHERE NOT EXISTS (SELECT 1 FROM finances WHERE id = 1);

INSERT INTO finances (created_at, updated_at, amount, category, note, status, type, created_by, updated_by, is_deleted)
SELECT '2026-01-22 14:20:45','2026-01-22 14:20:45',1200,'Rent','January rent','Success','Expense',1,NULL,false
WHERE NOT EXISTS (SELECT 1 FROM finances WHERE id = 2);

INSERT INTO finances (created_at, updated_at, amount, category, note, status, type, created_by, updated_by, is_deleted)
SELECT '2026-02-03 19:05:33','2026-02-03 19:05:33',350,'Food','Dinner out','Success','Expense',1,NULL,false
WHERE NOT EXISTS (SELECT 1 FROM finances WHERE id = 3);

INSERT INTO finances (created_at, updated_at, amount, category, note, status, type, created_by, updated_by, is_deleted)
SELECT '2026-02-10 11:40:22','2026-02-10 11:40:22',800,'Maintenance','AC repair','Success','Expense',1,NULL,false
WHERE NOT EXISTS (SELECT 1 FROM finances WHERE id = 4);

INSERT INTO finances (created_at, updated_at, amount, category, note, status, type, created_by, updated_by, is_deleted)
SELECT '2026-02-15 08:10:55','2026-02-15 08:10:55',150,'Other','Stationery purchase','Success','Expense',1,NULL,false
WHERE NOT EXISTS (SELECT 1 FROM finances WHERE id = 5);

INSERT INTO finances (created_at, updated_at, amount, category, note, status, type, created_by, updated_by, is_deleted)
SELECT '2026-02-20 16:25:18','2026-02-20 16:25:18',2200,'Rent','February rent','Success','Expense',1,NULL,false
WHERE NOT EXISTS (SELECT 1 FROM finances WHERE id = 6);

INSERT INTO finances (created_at, updated_at, amount, category, note, status, type, created_by, updated_by, is_deleted)
SELECT '2026-03-01 13:55:09','2026-03-01 13:55:09',450,'Food','Grocery shopping','Success','Expense',1,NULL,false
WHERE NOT EXISTS (SELECT 1 FROM finances WHERE id = 7);

INSERT INTO finances (created_at, updated_at, amount, category, note, status, type, created_by, updated_by, is_deleted)
SELECT '2026-03-05 21:30:41','2026-03-05 21:30:41',300,'Other','Online subscription','Success','Expense',1,NULL,false
WHERE NOT EXISTS (SELECT 1 FROM finances WHERE id = 8);

INSERT INTO finances (created_at, updated_at, amount, category, note, status, type, created_by, updated_by, is_deleted)
SELECT '2026-03-10 10:05:12','2026-03-10 10:05:12',6000,'Salary','Freelance payment','Success','Income',1,NULL,false
WHERE NOT EXISTS (SELECT 1 FROM finances WHERE id = 9);

INSERT INTO finances (created_at, updated_at, amount, category, note, status, type, created_by, updated_by, is_deleted)
SELECT '2026-03-15 18:44:27','2026-03-15 18:44:27',700,'Maintenance','Car service','Success','Expense',1,NULL,false
WHERE NOT EXISTS (SELECT 1 FROM finances WHERE id = 10);

INSERT INTO finances (created_at, updated_at, amount, category, note, status, type, created_by, updated_by, is_deleted)
SELECT '2026-03-18 07:25:36','2026-03-18 07:25:36',250,'Food','Breakfast','Success','Expense',1,NULL,false
WHERE NOT EXISTS (SELECT 1 FROM finances WHERE id = 11);

INSERT INTO finances (created_at, updated_at, amount, category, note, status, type, created_by, updated_by, is_deleted)
SELECT '2026-03-22 12:12:12','2026-03-22 12:12:12',1800,'Rent','Partial rent','Success','Expense',1,NULL,false
WHERE NOT EXISTS (SELECT 1 FROM finances WHERE id = 12);

INSERT INTO finances (created_at, updated_at, amount, category, note, status, type, created_by, updated_by, is_deleted)
SELECT '2026-03-25 15:45:50','2026-03-25 15:45:50',950,'Maintenance','Electric repair','Success','Expense',1,NULL,false
WHERE NOT EXISTS (SELECT 1 FROM finances WHERE id = 13);

INSERT INTO finances (created_at, updated_at, amount, category, note, status, type, created_by, updated_by, is_deleted)
SELECT '2026-03-28 20:10:05','2026-03-28 20:10:05',400,'Other','Movie night','Success','Expense',1,NULL,false
WHERE NOT EXISTS (SELECT 1 FROM finances WHERE id = 14);

INSERT INTO finances (created_at, updated_at, amount, category, note, status, type, created_by, updated_by, is_deleted)
SELECT '2026-04-01 09:55:59','2026-04-01 09:55:59',5200,'Salary','Bonus received','Success','Income',1,NULL,false
WHERE NOT EXISTS (SELECT 1 FROM finances WHERE id = 15);

INSERT INTO finances (created_at, updated_at, amount, category, note, status, type, created_by, updated_by, is_deleted)
SELECT '2026-04-03 17:33:14','2026-04-03 17:33:14',1100,'Food','Bulk groceries','Success','Expense',1,NULL,false
WHERE NOT EXISTS (SELECT 1 FROM finances WHERE id = 16);

INSERT INTO finances (created_at, updated_at, amount, category, note, status, type, created_by, updated_by, is_deleted)
SELECT '2026-04-05 11:22:48','2026-04-05 11:22:48',275,'Other','Taxi fare','Success','Expense',1,NULL,false
WHERE NOT EXISTS (SELECT 1 FROM finances WHERE id = 17);

INSERT INTO finances (created_at, updated_at, amount, category, note, status, type, created_by, updated_by, is_deleted)
SELECT '2026-04-07 14:18:39','2026-04-07 14:18:39',1300,'Rent','Rent payment','Success','Expense',1,NULL,false
WHERE NOT EXISTS (SELECT 1 FROM finances WHERE id = 18);

INSERT INTO finances (created_at, updated_at, amount, category, note, status, type, created_by, updated_by, is_deleted)
SELECT '2026-04-09 19:59:59','2026-04-09 19:59:59',640,'Maintenance','Plumbing fix','Success','Expense',1,NULL,false
WHERE NOT EXISTS (SELECT 1 FROM finances WHERE id = 19);

INSERT INTO finances (created_at, updated_at, amount, category, note, status, type, created_by, updated_by, is_deleted)
SELECT '2026-04-12 08:08:08','2026-04-12 08:08:08',90,'Food','Tea and snacks','Success','Expense',1,NULL,false
WHERE NOT EXISTS (SELECT 1 FROM finances WHERE id = 20);