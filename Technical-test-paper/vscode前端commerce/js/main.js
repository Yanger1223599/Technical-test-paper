import { createApp, ref } from 'https://unpkg.com/vue@3.3.4/dist/vue.esm-browser.js';
import AuthCard from './components/AuthCard.js';
import ProductList from './components/ProductList.js';
import OrderHistory from './components/OrderHistory.js';

const App = {
  template: `
    <div class="container py-4">
      <AuthCard v-if="!user" @loginSuccess="setUser" />
      <div v-else>
        <div class="d-flex justify-content-between align-items-center mb-3">
          <h3>歡迎, {{ user.username }}</h3>
          <button class="btn btn-outline-danger btn-sm" @click="logout">登出</button>
        </div>
        <ProductList :user="user" />
        <OrderHistory :user="user" />
      </div>
    </div>
  `,
  setup() {
    const user = ref(JSON.parse(localStorage.getItem('user')) || null);

    const setUser = (u) => {
      user.value = u;
      localStorage.setItem('user', JSON.stringify(u));
    };

    const logout = () => {
      localStorage.removeItem('user');
      user.value = null;
    };

    return { user, setUser, logout };
  }
};

createApp(App).mount('#app');
