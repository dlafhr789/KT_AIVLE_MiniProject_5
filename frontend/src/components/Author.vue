<template>
    <div>
        <String
            label="UserId"
            v-model="value.userId"
            :editMode="editMode"
        />
        <String
            label="State"
            v-model="value.state"
            :editMode="editMode"
        />
        <String
            label="Portfolio"
            v-model="value.portfolio"
            :editMode="editMode"
        />
        <String
            label="Profile"
            v-model="value.profile"
            :editMode="editMode"
        />
        <v-row class="ma-0 pa-0">
            <v-spacer></v-spacer>
            <v-btn width="64px" color="primary" @click="save">
                저장
            </v-btn>
        </v-row>
    </div>
</template>


<script>
import BaseEntity from './base-ui/BaseEntity.vue'

export default {
    name: 'Author',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "authors",
        value: {
        }
    }),
    created(){
    },
    computed:{
    },
    methods: {
    },
}
</script>
