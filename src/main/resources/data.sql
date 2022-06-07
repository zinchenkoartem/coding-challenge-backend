insert into applicant (id, first_name, last_name, email, github_user_name) values
(1, 'Artem', 'Zinchenko', 'artemz@academysmart.com', 'zinchenkoartem'),
(2, 'Thorben', 'Janssen', 'thorben@academysmart.com', 'thjanssen'),
(3, 'Evgeny', 'Borisov', 'evgeny@academysmart.com', 'Jeka1978');

insert into project (id, name, repository_url, live_url, role, start_year,team_size,duration_value,duration_option,employment_mode,capacity, applicant_id) values
(1, 'Trust Event Solution', '', 'https://trusteventsolutions.com','Backend java developer', 2021, 8,15,'MONTH','EMPLOYED','FULL_TIME',1),
(2, 'Microsoft Cloudyn', '', 'https://azure.microsoft.com/en-us/services/cost-management/','Backend java developer', 2018, 15,36,'MONTH','EMPLOYED','FULL_TIME',1),
(3, 'BlackBerry Enterprise Service', '', 'https://www.blackberry.com/us/en','Backend java developer', 2017, 65, 12,'MONTH','EMPLOYED','FULL_TIME',2),
(4, 'Truven (IBM) Healthcare analytics', '', 'http://truvenhealth.com', 'Backend java developer', 2016, 5, 12,'MONTH','EMPLOYED','FULL_TIME',3);