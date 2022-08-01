# Getting Started

1. ### Create database
    * Create database, **name**:data-reuse
    * create table `station`
    ```
   CREATE TABLE `station` (
    `id` int NOT NULL AUTO_INCREMENT,
    `station` varchar(15) NOT NULL,
    `org` varchar(15) NOT NULL,
    `idle` tinyint NOT NULL DEFAULT '1',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8
   ```
    * create table `user`
    ```
    CREATE TABLE `user` (
      `id` int NOT NULL AUTO_INCREMENT,
      `email` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
      `display` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
      `role` varchar(20) NOT NULL,
      `identity` varchar(70) DEFAULT NULL,
      `tags` json DEFAULT NULL,
      `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'Forgerock1',
      `c_password` varchar(20) DEFAULT NULL,
      `station` varchar(10) DEFAULT NULL,
      `user_name` varchar(30) DEFAULT NULL,
      `first_name` varchar(15) DEFAULT NULL,
      `last_name` varchar(15) DEFAULT NULL,
      `mgr_team_list` varchar(50) DEFAULT NULL,
      `team` varchar(20) DEFAULT NULL,
      `DID` varchar(20) DEFAULT NULL,
      `personal_type` varchar(20) DEFAULT NULL,
      `name` varchar(20) DEFAULT NULL,
      `t_zone` varchar(20) DEFAULT NULL,
      `language` varchar(10) DEFAULT NULL,
      `manager_of_team` varchar(20) DEFAULT NULL,
      `telephone_number` varchar(20) DEFAULT NULL,
      PRIMARY KEY (`id`),
      UNIQUE KEY `address` (`email`),
      UNIQUE KEY `identity` (`identity`)
      ) ENGINE=InnoDB DEFAULT CHARSET=utf8
   ```
    * Create table `user_status`
    ```
    CREATE TABLE `user_status` (
      `id` int NOT NULL AUTO_INCREMENT,
      `org` varchar(20) NOT NULL,
      `start_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
      `user_id` int DEFAULT NULL,
      `uuid` varchar(36) DEFAULT NULL,
      `test_case` varchar(20) DEFAULT NULL,
      `station` varchar(10) DEFAULT NULL,
      PRIMARY KEY (`id`)
      ) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8
    ```

2. ### Update `application.properties` file
    ```
   spring.datasource.url=jdbc:mysql://*.*.*.*:3306/reuse_data?useSSL=true&useUnicode=true&characterEncoding=utf8
    spring.datasource.username=*
    spring.datasource.password=*
   ```


