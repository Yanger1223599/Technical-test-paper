import { createApp, ref } from 'https://unpkg.com/vue@3.3.4/dist/vue.esm-browser.js';
import EmployeeAuthCard from './components/EmployeeAuthCard.js';
import EmployeeDashboard from './components/EmployeeDashboard.js';
// 如果你之後有需要
// import EmployeeOrderManage from './components/EmployeeOrderManage.js';

const App = {
  template: `
    <div class="container py-4">
      <!-- 尚未登入 -->
      <EmployeeAuthCard
        v-if="!employee"
        @loginSuccess="setEmployee"
      />

      <!-- 已登入 -->
      <div v-else>
        <div class="d-flex justify-content-between align-items-center mb-3">
          <h3>員工後台，歡迎 {{ employee.username }}</h3>
          <button class="btn btn-outline-danger btn-sm" @click="logout">
            登出
          </button>
        </div>

        <!-- 員工後台主畫面 -->
        <EmployeeDashboard :employee="employee" />

        <!-- 之後可以擴充 -->
        <!-- <EmployeeOrderManage :employee="employee" /> -->
      </div>
    </div>
  `,
  setup() {
    // 🔐 只讀 employee，不跟顧客共用
    const employee = ref(
      JSON.parse(localStorage.getItem('employeeUser')) || null
    );

    const setEmployee = (emp) => {
      employee.value = emp;
      localStorage.setItem('employeeUser', JSON.stringify(emp));
    };

    const logout = () => {
      localStorage.removeItem('employeeUser');
      employee.value = null;
    };

    return {
      employee,
      setEmployee,
      logout
    };
  }
};

createApp(App).mount('#app');
