package model.Enum.ToolsType;

public enum AxeType {

    primaryAxe{
        @Override
        public int costEnergy(boolean succesfully, boolean Farming){
            if (!succesfully && Farming){
                return 3;
            }
            else if (!succesfully || Farming){
                return 4;
            }
            else {
                return 5;
            }
        }
    },

    copperyAxe{
        @Override
        public int costEnergy(boolean succesfully, boolean Farming){
            if (!succesfully && Farming){
                return 2;
            }
            else if (!succesfully || Farming){
                return 3;
            }
            else {
                return 4;
            }
        }
    },

    ironAxe{
        @Override
        public int costEnergy(boolean succesfully, boolean Farming){
            if (!succesfully && Farming){
                return 1;
            }
            else if (!succesfully || Farming){
                return 2;
            }
            else {
                return 3;
            }
        }
    },

    goldenAxe{
        @Override
        public int costEnergy(boolean succesfully, boolean Farming){
            if (!succesfully && Farming){
                return 0;
            }
            else if (!succesfully || Farming){
                return 1;
            }
            else {
                return 2;
            }
        }
    },

    iridiumAxe{
        @Override
        public int costEnergy(boolean succesfully, boolean Farming){
            if (!succesfully && Farming){
                return 0;
            }
            else if (!succesfully || Farming){
                return 0;
            }
            else {
                return 1;
            }
        }
    };

    public abstract int costEnergy(boolean successfully, boolean farming/* is Farming is at top level?*/ );
}
