drop database if exists ssafy_mobile_cafe;
select @@global.transaction_isolation, @@transaction_isolation;
set @@transaction_isolation="read-committed";

create database ssafy_mobile_cafe;
use ssafy_mobile_cafe;

create table t_user(
	id varchar(100) primary key,
    name varchar(100) not null,
    pass varchar(100) not null,
    cash Integer default 0,
    stamps integer default 0,
    ftoken varchar(1024) default "",
    admin integer default 0
);

create table t_card(
	id integer auto_increment primary key,
    user_id varchar(100) not null,
    order_id integer not null,
    payment integer default 0 not null,
    content varchar(100) default "",
    pay_time timestamp default CURRENT_TIMESTAMP
    );
    
create table t_product(
	id integer auto_increment primary key,
    name varchar(100) not null,
    type varchar(20) not null,
    price integer not null,
    img varchar(100) not null
);


create  table t_order(
	o_id integer auto_increment primary key,
    user_id varchar(100) not null,
    order_table varchar(20),
    order_time timestamp default CURRENT_TIMESTAMP,    
    completed char(1) default 'N',
    constraint fk_order_user foreign key (user_id) references t_user(id) on delete cascade
);

create table t_order_detail(
	d_id integer auto_increment primary key,
    order_id integer not null,
    product_id integer not null,
    quantity integer not null default 1,
    constraint fk_order_detail_product foreign key (product_id) references t_product(id) on delete cascade,
    constraint fk_order_detail_order foreign key(order_id) references t_order(o_id) on delete cascade
);                                                 

create table t_stamp(
	id integer auto_increment primary key,
    user_id varchar(100) not null,
    order_id integer not null,
    quantity integer not null default 1,
    constraint fk_stamp_user foreign key (user_id) references t_user(id) on delete cascade,
    constraint fk_stamp_order foreign key (order_id) references t_order(o_id) on delete cascade
);

create table t_comment(
	id integer auto_increment primary key,
    user_id varchar(100) not null,
    product_id integer not null,
    rating float not null default 1,
    comment varchar(200),
    constraint fk_comment_user foreign key(user_id) references t_user(id) on delete cascade,
    constraint fk_comment_product foreign key(product_id) references t_product(id) on delete cascade
);

create table t_notification(
id integer auto_increment primary key,
user_id varchar(100) not null,
title varchar(100) not null,
content varchar(100) not null,
time timestamp default CURRENT_TIMESTAMP);


INSERT INTO t_user (id, name, pass, stamps) VALUES ('ssafy01', '김싸피', 'pass01', 5);
INSERT INTO t_user (id, name, pass, stamps) VALUES ('ssafy02', '황원태', 'pass02', 0);
INSERT INTO t_user (id, name, pass, stamps) VALUES ('ssafy03', '한정일', 'pass03', 3);
INSERT INTO t_user (id, name, pass, stamps) VALUES ('ssafy04', '반장운', 'pass04', 4);
INSERT INTO t_user (id, name, pass, stamps) VALUES ('ssafy05', '박하윤', 'pass05', 5);
INSERT INTO t_user (id, name, pass, stamps) VALUES ('ssafy06', '정비선', 'pass06', 6);
INSERT INTO t_user (id, name, pass, stamps) VALUES ('ssafy07', '김병관', 'pass07', 7);
INSERT INTO t_user (id, name, pass, stamps) VALUES ('ssafy08', '강석우', 'pass08', 8);
INSERT INTO t_user (id, name, pass, stamps) VALUES ('ssafy09', '견본무', 'pass09', 9);
INSERT INTO t_user (id, name, pass, stamps) VALUES ('ssafy10', '전인성', 'pass10', 20);
INSERT INTO t_user (id, name, pass, admin) VALUES ('admin', '관리자', '0000', 1);

INSERT INTO t_product (name, type, price, img) VALUES 
('아메리카노', 'coffee', 4100, 'coffee1.png'),
('카페라떼', 'coffee', 4500, 'coffee2.png'),
('카라멜 마끼아또', 'coffee', 4800, 'coffee3.png'),
('카푸치노', 'coffee', 4800, 'coffee4.png'),
('모카라떼', 'coffee', 4800, 'coffee5.png'),
('민트라떼', 'coffee', 4300, 'coffee6.png'),
('화이트 모카라떼', 'coffee', 4800, 'coffee7.png'),
('자몽에이드', 'coffee', 5100, 'coffee8.png'),
('레몬에이드', 'coffee', 5100, 'coffee9.png'),
('감귤에이드', 'coffee', 5100, 'coffee10.png'),
('흑임자라떼', 'coffee', 5500, 'coffee11.png'),
('콜드브루', 'coffee', 5000, 'coffee12.png'),
('연유라떼', 'coffee', 5400, 'coffee13.png'),
('초코칩 쿠키', 'dessert', 1500, 'cookie1.png'),
('마카다미아 쿠키', 'dessert', 1500, 'cookie2.png'),
('프렌치 크로플', 'dessert', 2000, 'dessert1.png'),
('누텔라 크로플', 'dessert', 2500, 'dessert2.png'),
('생크림 딸기 크로플', 'dessert', 3800, 'dessert3.png');

commit;

INSERT INTO t_order (user_id, order_table, completed) VALUES ('ssafy01', 'order_table 01', 'Y');
INSERT INTO t_order (user_id, order_table, completed) VALUES ('ssafy01', 'order_table 02', 'Y');
INSERT INTO t_order (user_id, order_table, completed) VALUES ('ssafy03', 'order_table 03', 'Y');
INSERT INTO t_order (user_id, order_table, completed) VALUES ('ssafy04', 'order_table 04', 'Y');
INSERT INTO t_order (user_id, order_table, completed) VALUES ('ssafy05', 'order_table 05', 'Y');
INSERT INTO t_order (user_id, order_table, completed) VALUES ('ssafy06', 'order_table 06', 'Y');
INSERT INTO t_order (user_id, order_table, completed) VALUES ('ssafy07', 'order_table 07', 'Y');
INSERT INTO t_order (user_id, order_table, completed) VALUES ('ssafy08', 'order_table 08', 'Y');
INSERT INTO t_order (user_id, order_table, completed) VALUES ('ssafy09', 'order_table 09', 'Y');
INSERT INTO t_order (user_id, order_table, completed) VALUES ('ssafy10', 'order_table 10', 'Y');
INSERT INTO t_order (user_id, order_table, completed) VALUES ('ssafy07', 'order_table 11', 'Y');

INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (1, 1, 10);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (1, 2, 8);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (2, 8, 10);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (2, 11, 14);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (3, 3, 30);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (3, 12, 13);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (4, 4, 15);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (4, 13, 9);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (5, 5, 25);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (5, 14, 16);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (6, 6, 17);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (6, 15, 18);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (7, 7, 27);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (7, 16, 25);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (8, 8, 2);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (8, 17, 15);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (9, 9, 19);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (9, 18, 21);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (10, 8, 4);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (10, 10, 16);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (11, 13, 13);

INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy01', 1, 6, '평범한 아메리카노맛');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy03', 1, 7, '아메리카노 향이 괜찮네요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy09', 1, 8, '아침마다 테이크 아웃하기 좋은 맛이네요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy05', 2, 4, '비교적 평범합니다');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy06', 2, 3, '약간 밍밍한 것 같네요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy02', 2, 5, '그럭저럭 먹을만 합니다');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy01', 3, 10, '이 주변에서 카라멜 마끼아또 젤 잘하는 집');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy01', 3, 8, '저한텐 조금 달지만 맛이 좋아요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy04', 3, 9, '달달한 거 먹고 싶을 때 강추!!');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy09', 4, 4, '카푸치노 만드는데 15분이나 걸리네요..');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy03', 4, 1, '카푸치노는 안먹는걸로...ㅎ');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy04', 4, 2, '카푸치노 맛이 이상해요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy08', 5, 7, '달달하고 좋네요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy07', 5, 8, '나쁘지 않아요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy08', 5, 7, '2번째 시켜봅니당');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy05', 6, 9, '민트는 사랑입니다');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy06', 6, 10, '민트 프라푸치노가 없어서 아쉽지만 좋아요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy02', 6, 1, '내기 걸려서 시켰는데 치약맛 싫어요 ㅠ');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy05', 7, 9, '화이트 모카라떼 너무 맛있어요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy10', 7, 10, '저는 이것만 먹습니다.');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy10', 7, 10, '오늘도 역시 모카라떼^^');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy05', 8, 2, '자몽이 조금 오래된 것 같아요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy06', 8, 3, '자몽에이드 맛이 예상과 달라서 조금..');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy04', 8, 4, '그럭저럭이긴 한데 다른메뉴 먹는게 나을듯합니다');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy08', 8, 3, '제 입맛에는 안맞습니당');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy08', 9, 8, '레몬에이드 원샷 때렸습니다.');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy03', 9, 10, '레몬에이드는 역시 싸피카페');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy04', 9, 8, '자몽 보다는 레몬이죠');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy02', 9, 9, '맛있어서 놀랐어요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy01', 9, 10, '저희 부모님도 여기 레몬에이드는 잘드심');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy01', 10, 8, '감귤이 생각보다 맛있어요!');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy10', 10, 5, '그냥 그래요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy05', 10, 10, '존맛탱');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy02', 11, 8, '흑임자라떼는 처음인데 괜찮네요!');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy03', 11, 7, '맛있습니다!');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy07', 11, 5, '그럭저럭~');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy01', 12, 10, '맛이 깊어요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy09', 12, 10, '진짜 맛있네요!');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy08', 12, 9, '추천합니다');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy06', 12, 8, '깊은 맛이 나요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy04', 13, 9, '달달하니 맛있어요!');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy07', 13, 9, '어린애들도 좋아할거 같아요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy08', 13, 2, '너무 달아요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy01', 14, 10, '촉촉합니다');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy09', 14, 9, '가성비 좋아요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy08', 14, 8, '좋아요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy06', 14, 10, '개꿀맛');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy02', 14, 9, '무난한듯');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy10', 15, 10, '초코칩쿠키보다 맛있어요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy01', 15, 9, '굉장히 맛있습니다');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy07', 16, 4, '심심해요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy03', 16, 2, '눅눅해요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy08', 16, 3, '맨날 재고가 부족하데요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy09', 17, 10, '역시 누텔라');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy04', 17, 1, '너무 다네요...');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy06', 17, 8, '아이들이 좋아해요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy08', 17, 3, '오래걸렸어요..');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy05', 18, 9, '딸기 맛있네요');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('ssafy07', 18, 8, '괜찮은 선택');

INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('ssafy01', 1, 4);
INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('ssafy01', 2, 1);
INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('ssafy03', 3, 3);
INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('ssafy04', 4, 4);
INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('ssafy05', 5, 5);
INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('ssafy06', 6, 6);
INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('ssafy07', 7, 7);
INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('ssafy08', 8, 8);
INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('ssafy09', 9, 9);
INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('ssafy10', 10, 20);
INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('ssafy07', 11, 7);

INSERT INTO t_notification (user_id, title, content) VALUES('ssafy01','주문완료','주문이 완료되었습니다.');
INSERT INTO t_notification (user_id, title, content) VALUES('ssafy01', '상품준비완료','주문하신 상품이 준비완료되었습니다.');

commit;       