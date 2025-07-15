package com.thungcam.chacalang.constant;

public class AuthConst {

    public static class VALIDATE {
        public static final String REGEX_EMAIL = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        public static final String REGEX_PHONE = "^[0-9]{10,11}$";
        public static final String REGEX_PASSWORD = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{6,255}$";
    }

    public static class ERROR {
        public static final String ERROR = "Đã xảy ra lỗi, vui lòng thử lại sau.";
        public static final String EMAIL_ALREADY_EXISTS = "Email đã tồn tại trong hệ thống.";
        public static final String EMAIL_NOT_EXISTS = "Email không tồn tại trong hệ thống.";
        public static final String PHONE_ALREADY_EXISTS = "Số điện thoại đã tồn tại trong hệ thống.";
        public static final String INVALID_EMAIL_FORMAT = "Định dạng email không hợp lệ.";
        public static final String INVALID_PHONE_FORMAT = "Định dạng số điện thoại không hợp lệ.";
        public static final String INVALID_PASSWORD_FORMAT = "Mật khẩu phải chứa ít nhất 6 ký tự bao gồm chữ hoa, chữ thường và số.";
        public static final String PASSWORD_NOT_MATCH = "Mật khẩu không khớp.";
        public static final String CURRENT_PASSWORD_INCORRECT = "Mật khẩu hiện tại không đúng.";
        public static final String CONFIRM_PASSWORD_NOT_MATCH = "Mật khẩu xác nhận không khớp.";
        public static final String USERNAME_ALREADY_EXISTS = "Tên đăng nhập đã tồn tại trong hệ thống.";
        public static final String INVALID_TOKEN = "Token không hợp lệ hoặc đã hết hạn.";
        public static final String OTP_NOT_FOUND = "Không tìm thấy mã OTP.";
        public static final String OTP_INCORRECT_OR_EXPIRED = "Mã OTP không đúng hoặc đã hết hạn.";
        public static final String UPDATE_PROFILE_FAILED = "Cập nhật thông tin thất bại.";
        public static final String USER_NOT_FOUND = "Không tìm thấy người dùng.";
        public static final String BRANCH_NOT_FOUND = "Không tìm thấy chi nhánh.";
        public static final String CANNOT_DELETE_SHIPPER = "Không thể xóa shipper.";
    }

    public static class MESSAGE {
        public static final String REGISTER_SUCCESS = "Đăng ký thành công, vui lòng kiểm tra email để xác thực OTP.";
        public static final String VERIFY_SUCCESS = "Xác thực thành công, vui lòng đăng nhập.";
        public static final String OTP_SENT = "Đã gửi mã OTP đến email của bạn.";
        public static final String RESET_PASSWORD_SUCCESS = "Đặt lại mật khẩu thành công, vui lòng đăng nhập.";
        public static final String UPDATE_PASSWORD_SUCCESS = "Cập nhật mật khẩu thành công.";
        public static final String UPDATE_PROFILE_SUCCESS = "Cập nhật thông tin thành công.";
        public static final String UPDATE_ADDRESS_SUCCESS = "Cập nhật địa chỉ thành công.";
        public static final String SAVE_ADDRESS_SUCCESS = "Lưu địa chỉ thành công.";
        public static final String DELETE_ADDRESS_SUCCESS = "Xóa địa chỉ thành công.";
        public static final String UPDATE_STATUS_SUCCESS = "Cập nhật trạng thái thành công.";
        public static final String ORDER_CANCEL_SUCCESS = "Hủy đơn hàng thành công.";
        public static final String RATE_SUCCESS = "Đánh giá của bạn đã được ghi nhận!";
        public static final String DELETE_STAFF_SUCCESS = "Xóa nhân viên thành công!";
    }

}
