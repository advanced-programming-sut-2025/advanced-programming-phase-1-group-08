package model.Enum.ToolsType;

public enum AxeType {

    primary{
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

    coppery{
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

    iron{
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

    golden{
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

    iridium{
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
