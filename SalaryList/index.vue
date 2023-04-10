<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">员工编号</label>
        <el-input v-model="query.deployNo" clearable placeholder="员工编号" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">员工姓名</label>
        <el-input v-model="query.deployeeName" clearable placeholder="员工姓名" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="员工编号" prop="deployNo">
            <el-input v-model="form.deployNo" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="基础薪资">
            <el-input v-model="form.basic" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="绩效">
            <el-input v-model="form.performance" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="总薪资">
            <el-input v-model="form.total" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="是否删除">
            <el-input v-model="form.isdelete" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="创建人">
            <el-input v-model="form.createby" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="更新人">
            <el-input v-model="form.updateby" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="员工姓名">
            <el-input v-model="form.deployeeName" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="deployNo" label="员工编号" />
        <el-table-column prop="basic" label="基础薪资" />
        <el-table-column prop="performance" label="绩效" />
        <el-table-column prop="total" label="总薪资" />
        <el-table-column prop="isdelete" label="是否删除" />
        <el-table-column prop="createby" label="创建人" />
        <el-table-column prop="updateby" label="更新人" />
        <el-table-column prop="deployeeName" label="员工姓名" />
        <el-table-column v-if="checkPer(['admin','salary:edit','salary:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudSalary from '@/api/salary'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { deployNo: null, basic: null, performance: null, total: null, isdelete: null, createby: null, updateby: null, deployeeName: null }
export default {
  name: 'Salary',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'salary', url: 'api/salary', idField: 'deployNo', sort: 'deployNo,desc', crudMethod: { ...crudSalary }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'salary:add'],
        edit: ['admin', 'salary:edit'],
        del: ['admin', 'salary:del']
      },
      rules: {
        deployNo: [
          { required: true, message: '员工编号不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'deployNo', display_name: '员工编号' },
        { key: 'deployeeName', display_name: '员工姓名' }
      ]
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
