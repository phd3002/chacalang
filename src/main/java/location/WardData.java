package location;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class WardData {

    @Getter
    private static final Map<String, List<String>> wardMap = Map.ofEntries(
            Map.entry("Ba Đình", List.of(
                    "Phúc Xá", "Trúc Bạch", "Vĩnh Phúc", "Cống Vị", "Liễu Giai",
                    "Ngọc Hà", "Điện Biên", "Đội Cấn", "Ngọc Khánh", "Kim Mã",
                    "Giảng Võ", "Thành Công"
            )),
            Map.entry("Hai Bà Trưng", List.of(
                    "Phạm Đình Hổ", "Nguyễn Du", "Bạch Mai", "Cầu Dền", "Đồng Tâm",
                    "Vĩnh Tuy", "Bách Khoa", "Quỳnh Lôi", "Quỳnh Mai", "Thanh Nhàn",
                    "Lê Đại Hành", "Đồng Nhân", "Phố Huế", "Bùi Thị Xuân", "Minh Khai",
                    "Trương Định", "Thanh Lương", "Thanh Trì"
            )),
            Map.entry("Cầu Giấy", List.of(
                    "Dịch Vọng", "Dịch Vọng Hậu", "Mai Dịch", "Nghĩa Đô",
                    "Nghĩa Tân", "Quan Hoa", "Trung Hòa", "Yên Hòa"
            )),
            Map.entry("Nam Từ Liêm", List.of(
                    "Cầu Diễn", "Đại Mỗ", "Mễ Trì", "Mỹ Đình 1",
                    "Mỹ Đình 2", "Phú Đô", "Phương Canh", "Tây Mỗ",
                    "Trung Văn", "Xuân Phương"
            )),
            Map.entry("Hoàng Mai", List.of(
                    "Đại Kim", "Định Công", "Giáp Bát", "Hoàng Liệt",
                    "Hoàng Văn Thụ", "Lĩnh Nam", "Mai Động", "Tân Mai",
                    "Thanh Trì", "Thịnh Liệt", "Trần Phú", "Tương Mai",
                    "Vĩnh Hưng", "Yên Sở"
            )),
            Map.entry("Hà Đông", List.of(
                    "Biên Giang", "Dương Nội", "Đồng Mai", "Hà Cầu",
                    "Kiến Hưng", "La Khê", "Mộ Lao", "Nguyễn Trãi",
                    "Phú La", "Phú Lãm", "Phú Lương", "Quang Trung",
                    "Văn Quán", "Vạn Phúc", "Yên Nghĩa", "Yết Kiêu"
            )),
            Map.entry("Long Biên", List.of(
                    "Bồ Đề", "Cự Khối", "Đức Giang", "Gia Thụy",
                    "Giang Biên", "Long Biên", "Ngọc Lâm", "Ngọc Thụy",
                    "Phúc Đồng", "Phúc Lợi", "Sài Đồng", "Thạch Bàn",
                    "Thượng Thanh", "Việt Hưng"
            )),
            Map.entry("Gia Lâm", List.of(
                    "Bát Tràng", "Cổ Bi", "Đa Tốn", "Đặng Xá",
                    "Dương Hà", "Dương Quang", "Dương Xá", "Kiêu Kỵ",
                    "Kim Lan", "Kim Sơn", "Lệ Chi", "Ninh Hiệp",
                    "Phú Thị", "Trâu Quỳ", "Văn Đức", "Yên Thường",
                    "Yên Viên"
            )),
            Map.entry("Hoài Đức", List.of(
                    "An Khánh", "An Thượng", "Cát Quế", "Di Trạch",
                    "Đắc Sở", "Đông La", "Đức Giang", "Đức Thượng",
                    "Kim Chung", "La Phù", "Lại Yên", "Minh Khai",
                    "Sơn Đồng", "Song Phương", "Tiền Yên", "Vân Canh",
                    "Vân Côn", "Yên Sở"
            )),
            // Các quận/huyện lân cận bổ sung
            Map.entry("Đống Đa", List.of(
                    "Văn Miếu", "Trung Tự", "Thịnh Quang", "Trung Phụng", "Khương Thượng",
                    "Văn Chương", "Quốc Tử Giám", "Trung Liệt", "Hàng Bột", "Thổ Quan",
                    "Quang Trung", "Phương Mai", "Ngã Tư Sở", "Cát Linh", "Nam Đồng",
                    "Láng Thượng", "Láng Hạ", "Phương Liên", "Kim Liên", "Ô Chợ Dừa",
                    "Khâm Thiên"
            )),
            Map.entry("Thanh Xuân", List.of(
                    "Thanh Xuân Bắc", "Thanh Xuân Trung", "Thanh Xuân Nam",
                    "Thượng Đình", "Hạ Đình", "Khương Đình", "Khương Mai",
                    "Khương Trung", "Nhân Chính", "Phương Liệt", "Kim Giang"
            )),
            Map.entry("Thanh Trì", List.of(
                    "Văn Điển", "Tứ Hiệp", "Yên Mỹ", "Tam Hiệp", "Thanh Liệt",
                    "Hữu Hòa", "Duyên Hà", "Ngũ Hiệp", "Vĩnh Quỳnh", "Liên Ninh",
                    "Ngọc Hồi", "Đông Mỹ", "Đại Áng"
            ))
    );

    public static List<String> getWardsByDistrict(String districtName) {
        return wardMap.getOrDefault(districtName, List.of());
    }
}
