<template>

    <v-card outlined>
        <v-card-title>
            PlanPurchase
        </v-card-title>

        <v-card-text>
            <String label="Plan" v-model="value.plan" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="planPurchase"
            >
                PlanPurchase
            </v-btn>
            
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="close"
            >
                Close
            </v-btn>
        </v-card-actions>
    </v-card>

</template>

<script>

export default {
    name: 'PlanPurchaseCommand',
    components:{
    },
    props: {},
    data: () => ({
        editMode: true,
        value: {},
    }),
    created() {
        this.value.plan = '';
    },
    watch: {
    },
    methods: {
        planPurchase() {
            this.$emit('planPurchase', this.value);
        },
        close() {
            this.$emit('closeDialog');
        },
        change() {
            this.$emit("update:modelValue", this.value);
        },
    }
}
</script>

