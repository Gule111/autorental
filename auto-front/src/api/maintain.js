import requestHttp from "@/utils/request";

export default {
    async search(start,size,data) {
        return await requestHttp.post(`/auto/maintain/${start}/${size}`, data);
    },
    async save(data) {
        return await requestHttp.post(`/auto/maintain`, data);
    },
    async update(data) {
        return await requestHttp.put(`/auto/maintain`, data);
    },
    async delete(data) {
        return await requestHttp.delete(`/auto/maintain/${data}`);
    },
}