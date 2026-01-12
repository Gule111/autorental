import requestHttp from "@/utils/request";
export default {
    async search(start,size,data){
        return await requestHttp.post(`/auto/role/${start}/${size}`,data);
    },
    async save(data){
        return await requestHttp.post(`/auto/role`,data);
    },
    async update(data){
        return await requestHttp.put(`/auto/role`,data);
    },
    async hasUser(id){
        return await requestHttp.get(`/auto/role/hasUser/${id}`);
    },
    async delete(ids){
        return await requestHttp.delete(`/auto/role/${ids}`);
    },
    async permissionTree(param){
        return await requestHttp.get(`/auto/role/permissionTree`,param);
    },
    async assignPermission(roleId,permissionIds){
        return await requestHttp.get(`/auto/role/${roleId}/${permissionIds}`);
    },
    async list(){
        return await requestHttp.get(`/auto/role`);
    },
};