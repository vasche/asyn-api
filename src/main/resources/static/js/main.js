const http = Axios.create();

const app = Vue.createApp({
    data() {
        return {
            getResult: null,
        }
    },
    methods: {
        fortmatResponse(res) {
            return JSON.stringify(res, null, 2);
        },
        async getIndex() {
            try {
                const res = await http.get("");
                const result = {
                    status: res.status + "-" + res.statusText,
                    headers: res.headers,
                    data: res.data,
                };
                this.getResult = this.fortmatResponse(result);
            } catch (err) {
                this.getResult = this.fortmatResponse(err.response?.data) || err;
            }
        }
    }
})
const vm = app.mount('#app')
