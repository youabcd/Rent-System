// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import { Button } from 'vant'
import { Image as VanImage } from 'vant'
import { Form } from 'vant'
import { Field } from 'vant'
import { Swipe, SwipeItem } from 'vant'
import { Col, Row } from 'vant'
import { RadioGroup, Radio } from 'vant'
import { Checkbox, CheckboxGroup } from 'vant'
import { Search } from 'vant'
import { Tabbar, TabbarItem } from 'vant'
import { Uploader } from 'vant'
import { Cell, CellGroup } from 'vant'
import { Lazyload } from 'vant'
import axios from 'axios'
import { Grid, GridItem } from 'vant'
import { Sticky } from 'vant'
import 'lib-flexible'
import { Icon } from 'vant'
import global from './components/Global.js'
import { Panel } from 'vant'
import { NavBar } from 'vant'
import { GoodsAction, GoodsActionIcon, GoodsActionButton } from 'vant'
import { NumberKeyboard } from 'vant'
import { List } from 'vant'
import { ActionSheet } from 'vant'
import { PullRefresh } from 'vant'
import { Tab, Tabs } from 'vant'
import { Popup } from 'vant'
import { Picker } from 'vant'
import { Collapse, CollapseItem } from 'vant'
import { Stepper } from 'vant'
import { DropdownMenu, DropdownItem } from 'vant'
import { SwipeCell } from 'vant'
import { Rate } from 'vant'

Vue.use(Rate)
Vue.use(SwipeCell)
Vue.use(List)
Vue.use(ActionSheet)
Vue.use(PullRefresh)
Vue.use(Tab)
Vue.use(Tabs)
Vue.use(Popup)
Vue.use(Picker)
Vue.use(Collapse)
Vue.use(CollapseItem)
Vue.use(Stepper)
Vue.use(DropdownMenu)
Vue.use(DropdownItem)
Vue.use(NumberKeyboard)
Vue.use(GoodsAction)
Vue.use(GoodsActionButton)
Vue.use(GoodsActionIcon)
Vue.use(NavBar)
Vue.use(Panel)
Vue.prototype.global=global;
Vue.use(Icon)
Vue.use(Sticky)
Vue.use(Grid)
Vue.use(GridItem)
Vue.prototype.$axios = axios
Vue.use(Lazyload)
Vue.use(Cell)
Vue.use(CellGroup)
Vue.use(Uploader)
Vue.use(Tabbar)
Vue.use(TabbarItem)
Vue.use(Search)
Vue.use(Checkbox)
Vue.use(CheckboxGroup)
Vue.use(Radio)
Vue.use(RadioGroup)
Vue.use(Col)
Vue.use(Row)
Vue.use(Swipe)
Vue.use(SwipeItem)
Vue.use(Field)
Vue.use(Form)
Vue.use(VanImage)
Vue.use(Button)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
