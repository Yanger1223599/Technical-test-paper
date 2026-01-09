import { computed } from 'https://unpkg.com/vue@3.3.4/dist/vue.esm-browser.js';

export default {
  name: 'CartSummary',
  props: ['cartItems'],
  template: `
    <div v-if="cartItems.length">
      <h5>購物車</h5>
      <ul class="list-group mb-2">
        <li class="list-group-item d-flex justify-content-between" v-for="item in cartItems" :key="item.productId">
          {{ item.productName }} x {{ item.quantity }}
          <span>{{ item.price*item.quantity }}</span>
        </li>
      </ul>
      <div class="d-flex justify-content-between fw-bold mb-2">
        <span>總金額</span><span>{{ totalPrice }}</span>
      </div>
      <button class="btn btn-success" @click="$emit('placeOrder')">建立訂單</button>
    </div>
  `,
  setup(props) {
    const totalPrice = computed(()=>props.cartItems.reduce((s,i)=>s+i.price*i.quantity,0));
    return { totalPrice };
  }
};
