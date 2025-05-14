// Constants
const API_HOST = "https://provinces.open-api.vn/api/";

// Get values from Thymeleaf
const selectedCity = /*[[${address != null ? address.city : ''}]]*/ '';
const selectedDistrict = /*[[${address != null ? address.district : ''}]]*/ '';
const selectedWard = /*[[${address != null ? address.ward : ''}]]*/ '';

// Initialize the address selector
document.addEventListener('DOMContentLoaded', function() {
    // Load provinces/cities on page load
    loadProvinces();
});

// Load provinces/cities
function loadProvinces() {
    axios.get(`${API_HOST}?depth=1`)
        .then(response => {
            renderSelect('city', response.data, selectedCity);
            if (selectedCity) {
                loadDistricts(selectedCity);
            }
        })
        .catch(error => {
            console.error('Error loading provinces:', error);
            showError('Không thể tải danh sách tỉnh/thành phố. Vui lòng thử lại sau.');
        });
}

// Load districts based on selected province
function loadDistricts(provinceCode) {
    axios.get(`${API_HOST}p/${provinceCode}?depth=2`)
        .then(response => {
            renderSelect('district', response.data.districts, selectedDistrict);
            if (selectedDistrict) {
                loadWards(selectedDistrict);
            }
        })
        .catch(error => {
            console.error('Error loading districts:', error);
            showError('Không thể tải danh sách quận/huyện. Vui lòng thử lại sau.');
        });
}

// Load wards based on selected district
function loadWards(districtCode) {
    axios.get(`${API_HOST}d/${districtCode}?depth=2`)
        .then(response => {
            renderSelect('ward', response.data.wards, selectedWard);
        })
        .catch(error => {
            console.error('Error loading wards:', error);
            showError('Không thể tải danh sách phường/xã. Vui lòng thử lại sau.');
        });
}

// Render select options
function renderSelect(elementId, data, selectedValue) {
    const select = document.getElementById(elementId);
    select.innerHTML = '<option value="" disabled selected>Chọn</option>';

    data.forEach(item => {
        const option = document.createElement('option');
        option.value = item.code;
        option.textContent = item.name;
        if (item.code === selectedValue) {
            option.selected = true;
        }
        select.appendChild(option);
    });
}

// Event listeners for select changes
document.getElementById('city').addEventListener('change', function() {
    const selectedCity = this.value;
    if (selectedCity) {
        loadDistricts(selectedCity);
        // Reset district and ward
        document.getElementById('district').innerHTML = '<option value="" disabled selected>Chọn quận/huyện</option>';
        document.getElementById('ward').innerHTML = '<option value="" disabled selected>Chọn phường/xã</option>';
    }
});

document.getElementById('district').addEventListener('change', function() {
    const selectedDistrict = this.value;
    if (selectedDistrict) {
        loadWards(selectedDistrict);
        // Reset ward
        document.getElementById('ward').innerHTML = '<option value="" disabled selected>Chọn phường/xã</option>';
    }
});

// Show error message
function showError(message) {
    // You can implement your preferred way of showing errors
    alert(message);
}

// Form validation
document.querySelector('form').addEventListener('submit', function(e) {
    const city = document.getElementById('city').value;
    const district = document.getElementById('district').value;
    const ward = document.getElementById('ward').value;
    const detailAddress = document.querySelector('input[name="detailAddress"]').value;
    const receiverName = document.querySelector('input[name="receiverName"]').value;
    const phoneNumber = document.querySelector('input[name="phoneNumber"]').value;

    if (!city || !district || !ward || !detailAddress || !receiverName || !phoneNumber) {
        e.preventDefault();
        showError('Vui lòng điền đầy đủ thông tin địa chỉ.');
    }

    // Validate phone number format
    const phoneRegex = /^[0-9]{10}$/;
    if (!phoneRegex.test(phoneNumber)) {
        e.preventDefault();
        showError('Số điện thoại không hợp lệ. Vui lòng nhập 10 chữ số.');
    }
});