import Vue from 'vue'
import Router from 'vue-router'
import RenterLogin from '@/components/RenterLogin'
import Register from '@/components/Register'
import ServiceLogin from '@/components/ServiceLogin'
import RepairerLogin from '@/components/RepairerLogin'
import Renter from '@/components/Renter'
import MyInfo from '@/components/MyInfo'
import Search from '@/components/Search'
import ConnectService from '@/components/ConnectService'
import Setting from '@/components/Setting'
import Change from '@/components/Change'
import CheckRoom from '@/components/CheckRoom'
import NeedToPay from '@/components/NeedToPay'
import Check_out from '@/components/Check_out'
import Room from '@/components/Room'
import Collection from '@/components/Collection'
import Paying from '@/components/Paying'
import Service from "@/components/Service.vue"
import CSOrders from '@/components/Services/CSOrders'
import CSUsers from '@/components/Services/CSUsers'
import CSRooms from '@/components/Services/CSRooms'
import CSWorkOrders from '@/components/Services/CSWorkOrders'
import about from '@/components/about'
import userHomeSP from '@/components/Services/userHomeSP'
import repairerHomeSP from '@/components/Services/repairerHomeSP'
import AvailableHouseList from '@/components/Services/AvailableHouseList'
import AllHouseList from '@/components/Services/AllHouseList'
import roomSP from '@/components/Services/roomSP'
import WorkOrderSP from '@/components/Services/WorkOrderSP'
import OrderSP from '@/components/Services/OrderSP'
import ExportContract from '@/components/Services/ExportContract'
import Renting from '@/components/Renting'
import Repair from '@/components/Repair'
import Repair_job from '@/components/Repair/Repair_job'
import Repair_report from '@/components/Repair/Repair_report'
import file from '@/components/file'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'RenterLogin',
      component: RenterLogin
    },
    {
      path: '/Register',
      name: 'Register',
      component: Register
    },
    {
      path: '/ServiceLogin',
      name: 'ServiceLogin',
      component: ServiceLogin
    },
    {
      path: '/RepairerLogin',
      name: 'RepairerLogin',
      component: RepairerLogin
    },
    {
      path: '/Renter',
      name: 'Renter',
      component: Renter
    },
    {
      path: '/MyInfo',
      name: 'MyInfo',
      component: MyInfo
    },
    {
      path: '/Service',
      name: 'Service',
      component: Service
    },
    {
      path: '/Search',
      name: 'Search',
      component: Search
    },
    {
      path: '/ConnectService',
      name: 'ConnectService',
      component: ConnectService
    },
    {
      path: '/Setting',
      name: 'Setting',
      component: Setting
    },
    {
      path: '/NeedToPay',
      name: 'NeedToPay',
      component: NeedToPay
    },
    {
      path: '/CheckRoom',
      name: 'CheckRoom',
      component: CheckRoom
    },
    {
      path: '/Change',
      name: 'Change',
      component: Change
    },
    {
      path: '/Check_out',
      name: 'Check_out',
      component: Check_out
    },
    {
      path: '/Room',
      name: 'Room',
      component: Room
    },
    {
      path: '/Paying',
      name: 'Paying',
      component: Paying
    },
    {
      path: '/Collection',
      name: 'Collection',
      component: Collection
    },
    {
        path: '/Service',
        name: 'Service',
        component: Service
    },
    {
        path: '/CS/CSOrders',
        name: 'CSOrders',
        component: CSOrders
    },
    {
        path: '/CS/CSUsers',
        name: 'CSUsers',
        component: CSUsers
    },
    {
        path: '/CS/CSRooms',
        name: 'CSRooms',
        component: CSRooms
    },
    {
        path: '/CS/CSWorkOrders',
        name: 'CSWorkOrders',
        component: CSWorkOrders
    },
    {
        path: '/about',
        name: 'about',
        component: about
    },
    {
        path: '/CS/userHomeSP/:id',
        name: 'userHomeSP',
        component: userHomeSP
    },
    {
        path: '/CS/repairerHomeSP/:id',
        name: 'repairerHomeSP',
        component: repairerHomeSP
    },
    {
        path: '/CS/CSRooms/Available',
        name: 'AvailableHouseList',
        component: AvailableHouseList
    },
    {
        path: '/CS/CSRooms/All',
        name: 'AllHouseList',
        component: AllHouseList
    },
    {
        path: '/CS/CSRooms/:id',
        name: 'roomSP',
        component: roomSP
    },
    {
        path: '/CS/CSWorkOrders/:id',
        name: 'WorkOrderSP',
        component: WorkOrderSP
    },
    {
        path: '/CS/CSOrders/:id',
        name: 'OrderSP',
        component: OrderSP
    },
    {
        path: '/CS/ExportContract/:id',
        name: 'ExportContract',
        component: ExportContract
    },
    {
        path: '/Renting',
        name: 'Renting',
        component: Renting
    },
    {
        path:"/Repair/Repair_job",
        name:'Repair_job',
        component:Repair_job
    },
    {
        path:"/Repair/Repair_report",
        name:'Repair_report',
        component:Repair_report
    },
    {
        path:"/Repairer",
        name:'Repair',
        component:Repair
    },
    {
        path:"/file",
        name:'file',
        component:file
    },
  ]
})
