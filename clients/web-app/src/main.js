import Vue from 'vue'

Vue.config.productionTip = false;

import heUI from "./components/heui"
import "./components/heui/default-theme/index.less"
Vue.use(heUI);

import 'font-awesome/css/font-awesome.min.css'

import app from "./app"

app.install();