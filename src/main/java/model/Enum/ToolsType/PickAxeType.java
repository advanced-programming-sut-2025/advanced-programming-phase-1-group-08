package model.Enum.ToolsType;

public enum PickAxeType {

    primary{
        @Override
        public int costEnergy(boolean succesfully, boolean mining){
            if (!succesfully && mining){
                return 3;
            }
            else if (!succesfully || mining){
                return 4;
            }
            else {
                return 5;
            }
        }
    },

    coppery{
        @Override
        public int costEnergy(boolean succesfully, boolean mining){
            if (!succesfully && mining){
                return 2;
            }
            else if (!succesfully || mining){
                return 3;
            }
            else {
                return 4;
            }
        }
    },

    iron{
        @Override
        public int costEnergy(boolean succesfully, boolean mining){
            if (!succesfully && mining){
                return 1;
            }
            else if (!succesfully || mining){
                return 2;
            }
            else {
                return 3;
            }
        }
    },

    golden{
        @Override
        public int costEnergy(boolean succesfully, boolean mining){
            if (!succesfully && mining){
                return 0;
            }
            else if (!succesfully || mining){
                return 1;
            }
            else {
                return 2;
            }
        }
    },

    iridium{
        @Override
        public int costEnergy(boolean succesfully, boolean mining){
            if (!succesfully && mining){
                return 0;
            }
            else if (!succesfully || mining){
                return 0;
            }
            else {
                return 1;
            }
        }
    };

    public abstract int costEnergy(boolean successfully, boolean mining/* is Mining is at top level?*/ );
}
