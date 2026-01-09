import { ref, onMounted } from 'https://unpkg.com/vue@3.3.4/dist/vue.esm-browser.js';

export default {
  name:'OrderHistory',
  props:['user'],
  template: `
    <div class="mt-5">
      <h4>我的訂單</h4>
      <table class="table table-bordered" v-if="orders.length">
        <thead><tr>
          <th>訂單編號</th><th>總金額</th><th>付款狀態</th>
        </tr></thead>
        <tbody>
          <tr v-for="o in orders" :key="o.orderId">
            <td>{{ o.orderId }}</td>
            <td>{{ o.price }}</td>
            <td>{{ o.payStatus ? '已付款' : '未付款' }}</td>
          </tr>
        </tbody>
      </table>
      <div v-else>尚無訂單紀錄</div>
    </div>
  `,
  setup(props){
    const orders = ref([]);
    const fetchOrders = async ()=>{
      try{
        const res = await fetch(`http://localhost:8080/api/orders/member/${props.user.empNo}`);
        orders.value = await res.json();
      }catch(e){console.error(e);}
    };
    onMounted(fetchOrders);
    return { orders };
  }
};
