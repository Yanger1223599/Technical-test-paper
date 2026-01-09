import { ref, onMounted } from 'https://unpkg.com/vue@3.3.4/dist/vue.esm-browser.js';
import CartSummary from './CartSummary.js';

export default {
  name: 'ProductList',
  props: ['user'],
  components: { CartSummary },
  template: `
    <div>
      <table class="table table-bordered">
        <thead><tr>
          <th>商品編號</th><th>商品名稱</th><th>售價</th><th>庫存</th><th>數量</th><th>操作</th>
        </tr></thead>
        <tbody>
          <tr v-for="p in products" :key="p.id">
            <td>{{ p.productId }}</td>
            <td>{{ p.productName }}</td>
            <td>{{ p.price }}</td>
            <td>{{ p.quantity }}</td>
            <td><input type="number" v-model.number="p.cartQty" min="1" :max="p.quantity" class="form-control"></td>
            <td><button class="btn btn-sm btn-primary" @click="addToCart(p)">加入購物車</button></td>
          </tr>
        </tbody>
      </table>
      <CartSummary :cartItems="cartItems" @placeOrder="placeOrder"/>
    </div>
  `,
  setup(props) {
    const products = ref([]);
    const cartItems = ref([]);

    const fetchProducts = async () => {
      const res = await fetch('http://localhost:8080/api/products');
      const data = await res.json();
      products.value = data.filter(p => p.quantity>0).map(p => ({ ...p, cartQty: 1 }));
    };

    const addToCart = (p) => {
      if (p.cartQty > p.quantity) return alert('數量不可大於庫存');
      const idx = cartItems.value.findIndex(c => c.productId===p.productId);
      if (idx>=0) cartItems.value[idx].quantity += p.cartQty;
      else cartItems.value.push({ ...p, quantity: p.cartQty });
      p.cartQty=1;
    };

    const placeOrder = async () => {
      if (!cartItems.value.length) return alert('購物車為空');
      const orderData = {
        memberId: props.user.empNo,
        price: cartItems.value.reduce((s,i)=>s+i.price*i.quantity,0),
        payStatus: false,
        orderDetails: cartItems.value.map(i=>({
          productId:i.productId,
          quantity:i.quantity,
          price:i.price,
          orderPrice:i.price*i.quantity
        }))
      };
      try {
        const res = await fetch('http://localhost:8080/api/orders', {
          method:'POST',
          headers:{'Content-Type':'application/json'},
          body:JSON.stringify(orderData)
        });
        if(!res.ok) throw new Error(await res.text());
        await res.json();
        alert('訂單建立成功');
        cartItems.value=[];
        fetchProducts();
      } catch(e){alert('訂單建立失敗'); console.error(e);}
    };

    onMounted(fetchProducts);
    return { products, cartItems, addToCart, placeOrder };
  }
};
