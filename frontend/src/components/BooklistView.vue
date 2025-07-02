<template>
    <v-data-table
        :headers="headers"
        :items="items"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>
</template>

<script>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { VDataTable } from 'vuetify/labs/VDataTable'

export default {
    name: 'BooklistView',
    components: {
        VDataTable,
    },
    props: {
        value: Object,
        editMode: Boolean,
        isNew: Boolean
    },
    setup() {
        const headers = ref([
            // 필드 디스크립터를 기반으로 헤더 설정
            { title: "id", key: "bookId" },
            { title: "title", key: "title" },
            { title: "author", key: "userId" },
            { title: "publishedAt", key: "publishedAt" },
            { title: "view", key: "view" },
            { title: "coverUrl", key: "coverUrl" },
            { title: "summary", key: "summary" },
            { title: "content", key: "content" },
            { title: "subscribers", key: "subscribers" },
        ]);

        const items = ref([]);

        onMounted(async () => {
            try {
                const response = await axios.get('/books');

                if (response.data && response.data._embedded && response.data._embedded.books) {
                    const data = response.data._embedded.books;

                    data.forEach(obj => {
                        if (obj._links && obj._links.self && obj._links.self.href) {
                            obj.id = obj._links.self.href.split("/").pop();
                        }
                    });
                    
                    items.value = data;
                }

            } catch (error) {
                console.error('Error fetching data:', error);
            }
        });

        return {
            headers,
            items
        };
    }
}
</script>
