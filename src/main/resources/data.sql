-- roleテーブル
INSERT IGNORE INTO roles (id, name) VALUES (1, 'ROLE_GENERAL');
INSERT IGNORE INTO roles (id, name) VALUES (2, 'ROLE_ADMIN');
INSERT IGNORE INTO roles (id, name) VALUES (3, 'ROLE_PREMIUM');

-- categoryテーブル
INSERT IGNORE INTO category (id, name) VALUES (1, '居酒屋');
INSERT IGNORE INTO category (id, name) VALUES (2, '中華');
INSERT IGNORE INTO category (id, name) VALUES (3, 'イタリアン');
INSERT IGNORE INTO category (id, name) VALUES (4, 'スイーツ');
INSERT IGNORE INTO category (id, name) VALUES (5, '鍋');
INSERT IGNORE INTO category (id, name) VALUES (6, '焼肉');
INSERT IGNORE INTO category (id, name) VALUES (7, '海鮮');
INSERT IGNORE INTO category (id, name) VALUES (8, 'ステーキ');
INSERT IGNORE INTO category (id, name) VALUES (9, 'そば');
INSERT IGNORE INTO category (id, name) VALUES (10, 'カレー');
INSERT IGNORE INTO category (id, name) VALUES (11, '丼');
INSERT IGNORE INTO category (id, name) VALUES (12, '寿司');
INSERT IGNORE INTO category (id, name) VALUES (13, '喫茶店');
INSERT IGNORE INTO category (id, name) VALUES (14, 'ピザ');

-- housesテーブル
INSERT IGNORE INTO houses (id, name, image_name, description, price, category_id, price_min, price_max, opening_time, closing_time, holiday, capacity, postal_code, address, phone_number, created_at, updated_at) VALUES (1,'テストハウス01','house01.jpg','これはテスト用の説明です01',1000,1,1000,2000,'10:00:00','18:00:00','月曜日',10,'460-0001','名古屋市中区1-1-1','052-0000-001',NOW(),NOW());
INSERT IGNORE INTO houses (id, name, image_name, description, price, category_id, price_min, price_max, opening_time, closing_time, holiday, capacity, postal_code, address, phone_number, created_at, updated_at) VALUES (2,'テストハウス02','house02.jpg','これはテスト用の説明です02',2000,2,2000,3000,'11:00:00','19:00:00','火曜日',20,'460-0002','名古屋市中区1-1-02','052-0000-002',NOW(),NOW());
INSERT IGNORE INTO houses (id, name, image_name, description, price, category_id, price_min, price_max, opening_time, closing_time, holiday, capacity, postal_code, address, phone_number, created_at, updated_at) VALUES (3,'テストハウス03','house03.jpg','これはテスト用の説明です03',3000,3,3000,4000,'12:00:00','20:00:00','水曜日',30,'460-0003','名古屋市中区1-1-03','052-0000-003',NOW(),NOW());
INSERT IGNORE INTO houses (id, name, image_name, description, price, category_id, price_min, price_max, opening_time, closing_time, holiday, capacity, postal_code, address, phone_number, created_at, updated_at) VALUES (4,'テストハウス04','house04.jpg','これはテスト用の説明です04',4000,4,4000,5000,'10:00:00','21:00:00','木曜日',10,'460-0004','名古屋市中区1-1-04','052-0000-004',NOW(),NOW());
INSERT IGNORE INTO houses (id, name, image_name, description, price, category_id, price_min, price_max, opening_time, closing_time, holiday, capacity, postal_code, address, phone_number, created_at, updated_at) VALUES (5,'テストハウス05','house05.jpg','これはテスト用の説明です05',5000,5,5000,6000,'11:00:00','22:00:00','金曜日',20,'460-0005','名古屋市中区1-1-05','052-0000-005',NOW(),NOW());
INSERT IGNORE INTO houses (id, name, image_name, description, price, category_id, price_min, price_max, opening_time, closing_time, holiday, capacity, postal_code, address, phone_number, created_at, updated_at) VALUES (6,'テストハウス06','house06.jpg','これはテスト用の説明です06',6000,6,6000,7000,'12:00:00','18:00:00','土曜日',30,'460-0006','名古屋市中区1-1-06','052-0000-006',NOW(),NOW());
INSERT IGNORE INTO houses (id, name, image_name, description, price, category_id, price_min, price_max, opening_time, closing_time, holiday, capacity, postal_code, address, phone_number, created_at, updated_at) VALUES (7,'テストハウス07','house07.jpg','これはテスト用の説明です07',7000,7,7000,8000,'10:00:00','19:00:00','日曜日',10,'460-0007','名古屋市中区1-1-07','052-0000-007',NOW(),NOW());
INSERT IGNORE INTO houses (id, name, image_name, description, price, category_id, price_min, price_max, opening_time, closing_time, holiday, capacity, postal_code, address, phone_number, created_at, updated_at) VALUES (8,'テストハウス08','house08.jpg','これはテスト用の説明です08',8000,8,8000,9000,'11:00:00','20:00:00','月曜日',20,'460-0008','名古屋市中区1-1-08','052-0000-008',NOW(),NOW());
INSERT IGNORE INTO houses (id, name, image_name, description, price, category_id, price_min, price_max, opening_time, closing_time, holiday, capacity, postal_code, address, phone_number, created_at, updated_at) VALUES (9,'テストハウス09','house09.jpg','これはテスト用の説明です09',9000,9,9000,10000,'12:00:00','21:00:00','火曜日',30,'460-0009','名古屋市中区1-1-09','052-0000-009',NOW(),NOW());
INSERT IGNORE INTO houses (id, name, image_name, description, price, category_id, price_min, price_max, opening_time, closing_time, holiday, capacity, postal_code, address, phone_number, created_at, updated_at) VALUES (10,'テストハウス10','house10.jpg','これはテスト用の説明です10',10000,10,10000,11000,'10:00:00','22:00:00','水曜日',40,'460-0010','名古屋市中区1-1-10','052-0000-010',NOW(),NOW());

-- usersテーブル
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (1, '侍 太郎', 'サムライ タロウ', '101-0022', '東京都千代田区神田練塀町300番地', '090-1234-5678', 'taro.samurai@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 3, true);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (2, '侍 花子', 'サムライ ハナコ', '101-0022', '東京都千代田区神田練塀町300番地', '090-1234-5678', 'hanako.samurai@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 2, true);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (3, '侍 義勝', 'サムライ ヨシカツ', '638-0644', '奈良県五條市西吉野町湯川X-XX-XX', '090-1234-5678', 'yoshikatsu.samurai@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 1, true);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (4, '侍 幸美', 'サムライ サチミ', '342-0006', '埼玉県吉川市南広島X-XX-XX', '090-1234-5678', 'sachimi.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (5, '侍 雅', 'サムライ ミヤビ', '527-0209', '滋賀県東近江市佐目町X-XX-XX', '090-1234-5678', 'miyabi.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (6, '侍 正保', 'サムライ マサヤス', '989-1203', '宮城県柴田郡大河原町旭町X-XX-XX', '090-1234-5678', 'masayasu.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (7, '侍 真由美', 'サムライ マユミ', '951-8015', '新潟県新潟市松岡町X-XX-XX', '090-1234-5678', 'mayumi.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (8, '侍 安民', 'サムライ ヤスタミ', '241-0033', '神奈川県横浜市旭区今川町X-XX-XX', '090-1234-5678', 'yasutami.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (9, '侍 章緒', 'サムライ アキオ', '739-2103', '広島県東広島市高屋町宮領X-XX-XX', '090-1234-5678', 'akio.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (10, '侍 祐子', 'サムライ ユウコ', '601-0761', '京都府南丹市美山町高野X-XX-XX', '090-1234-5678', 'yuko.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (11, '侍 秋美', 'サムライ アキミ', '606-8235', '京都府京都市左京区田中西春菜町X-XX-XX', '090-1234-5678', 'akimi.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (12, '侍 信平', 'サムライ シンペイ', '673-1324', '兵庫県加東市新定X-XX-XX', '090-1234-5678', 'shinpei.samurai@example.com', 'password', 1, false);

-- reservationsテーブル
INSERT IGNORE INTO reservations (id, house_id, user_id, checkin_date, checkout_date, number_of_people, amount) VALUES (1, 1, 1, '2023-04-01 10:00:00', '2023-04-01 12:00:00', 2, 6000);
INSERT IGNORE INTO reservations (id, house_id, user_id, checkin_date, checkout_date, number_of_people, amount) VALUES (2, 2, 1, '2023-04-01 10:00:00', '2023-04-01 12:00:00', 3, 7000);
INSERT IGNORE INTO reservations (id, house_id, user_id, checkin_date, checkout_date, number_of_people, amount) VALUES (3, 3, 1, '2023-04-01 10:00:00', '2023-04-01 12:00:00', 4, 8000);
INSERT IGNORE INTO reservations (id, house_id, user_id, checkin_date, checkout_date, number_of_people, amount) VALUES (4, 4, 1, '2023-04-01 10:00:00', '2023-04-01 12:00:00', 5, 9000);
INSERT IGNORE INTO reservations (id, house_id, user_id, checkin_date, checkout_date, number_of_people, amount) VALUES (5, 5, 1, '2023-04-01 10:00:00', '2023-04-01 12:00:00', 6, 10000);
INSERT IGNORE INTO reservations (id, house_id, user_id, checkin_date, checkout_date, number_of_people, amount) VALUES (6, 6, 1, '2023-04-01 10:00:00', '2023-04-01 12:00:00', 2, 6000);
INSERT IGNORE INTO reservations (id, house_id, user_id, checkin_date, checkout_date, number_of_people, amount) VALUES (7, 7, 1, '2023-04-01 10:00:00', '2023-04-01 12:00:00', 3, 7000);
INSERT IGNORE INTO reservations (id, house_id, user_id, checkin_date, checkout_date, number_of_people, amount) VALUES (8, 8, 1, '2023-04-01 10:00:00', '2023-04-01 12:00:00', 4, 8000);
INSERT IGNORE INTO reservations (id, house_id, user_id, checkin_date, checkout_date, number_of_people, amount) VALUES (9, 9, 1, '2023-04-01 10:00:00', '2023-04-01 12:00:00', 5, 9000);
INSERT IGNORE INTO reservations (id, house_id, user_id, checkin_date, checkout_date, number_of_people, amount) VALUES (10, 10, 1, '2023-04-01 10:00:00', '2023-04-01 12:00:00', 6, 10000);
INSERT IGNORE INTO reservations (id, house_id, user_id, checkin_date, checkout_date, number_of_people, amount) VALUES (11, 11, 1, '2023-04-01 10:00:00', '2023-04-01 12:00:00', 2, 6000);

-- レビューテーブル kadai2
INSERT IGNORE INTO reviews (id, house_id, user_id, name, star, review_comment) VALUES (1, 1, 1, '太郎', 5, '素晴らしい料理でした。満足できました。');
INSERT IGNORE INTO reviews (id, house_id, user_id, name, star, review_comment) VALUES (2, 1, 2, '花子', 4, '快適に過ごせました。');
INSERT IGNORE INTO reviews (id, house_id, user_id, name, star, review_comment) VALUES (3, 1, 3, '田中', 4, 'アクセスが良く、観光に最適でした。');
INSERT IGNORE INTO reviews (id, house_id, user_id, name, star, review_comment) VALUES (4, 1, 4, '山田', 5, 'スタッフが親切で、気持ちよく食事できました。');
INSERT IGNORE INTO reviews (id, house_id, user_id, name, star, review_comment) VALUES (5, 1, 5, '加藤', 4, '駅から近く、便利でした。');
INSERT IGNORE INTO reviews (id, house_id, user_id, name, star, review_comment) VALUES (6, 1, 6, '佐藤', 5, '周辺の店が充実していて、楽しめました。');
INSERT IGNORE INTO reviews (id, house_id, user_id, name, star, review_comment) VALUES (7, 1, 7, '伊藤', 4, '料金が手頃で、コストパフォーマンスが良かったです。');
INSERT IGNORE INTO reviews (id, house_id, user_id, name, star, review_comment) VALUES (8, 1, 8, '鬼頭', 3, '静かな環境でゆっくり休めました。');
INSERT IGNORE INTO reviews (id, house_id, user_id, name, star, review_comment) VALUES (9, 1, 9, '内藤', 4, '設備が充実していました。');
INSERT IGNORE INTO reviews (id, house_id, user_id, name, star, review_comment) VALUES (10, 1, 10, '鈴木', 5, 'Wi-Fiが快適で助かりました。');
INSERT IGNORE INTO reviews (id, house_id, user_id, name, star, review_comment) VALUES (11, 1, 11, '後藤', 4, '清潔感があり、気持ちよく食事できました。');
