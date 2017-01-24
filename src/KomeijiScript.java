

import java.util.Random;

/**
 * Created by rei on 11/20/2016.
 */
public class KomeijiScript extends GameComponent implements GameSpriteAnimatorDelegates{
    KomeijiState state;
    KomeijiMovement movement;
    GameSpriteAnimator animator;
    final String IDLE = "idle";
    final String MOVERIGHT = "mover";
    final String MOVELEFT = "movel";
    final String ATK = "atk";
    GameObject lampu;
    @Override
    public int indexOnLoopFinish(String name) {
        if(name == IDLE)
            return 0;
        else
            return -1;

    }

    @Override
    public float spriteTimeForAnim(String name, int index) {
        if(name == IDLE )
            return 0.3f;
        else if (name == ATK)
            return 0.1f;
        else
            return 0.12f;

    }



    public KomeijiScript(GameObject obj){
        super(obj);
        KomeijiIdleAndShift a = new KomeijiIdleAndShift();
        a.idleTime = 3;
        a.shiftTo = new KomeijiFirstPhase();
        state = a;
        movement = new KomeijiMovement();
        System.out.println(gameObject.position.x + " " + gameObject.position.y);
        movement.dest = new Vec2(gameObject.position);
        animator = new GameSpriteAnimator(this);
        gameObject.renderer = new SpriteRenderer("assets/stg7enm.png", 12, 0, 43, 65);
        animator.renderer = gameObject.renderer;

        animator.addSpriteForAnim(IDLE, BufferedImageManager.getImage("assets/stg7enm.png", 12, 0, 43, 65));
        animator.addSpriteForAnim(IDLE, BufferedImageManager.getImage("assets/stg7enm.png", 74,0,46,64));
        animator.addSpriteForAnim(IDLE, BufferedImageManager.getImage("assets/stg7enm.png", 138,0,43,66));
        animator.addSpriteForAnim(IDLE, BufferedImageManager.getImage("assets/stg7enm.png", 202,0,42,66));


        animator.addSpriteForAnim(MOVELEFT, BufferedImageManager.getImage("assets/stg7enm.png", 12, 65, 44, 63));
        animator.addSpriteForAnim(MOVELEFT, BufferedImageManager.getImage("assets/stg7enm.png", 74,65,47,61));
        animator.addSpriteForAnim(MOVELEFT, BufferedImageManager.getImage("assets/stg7enm.png", 136,64,47,61));
        animator.addSpriteForAnim(MOVELEFT, BufferedImageManager.getImage("assets/stg7enm.png", 200,65,47,60));

        animator.addSpriteForAnim(MOVERIGHT, BufferedImageManager.getImage("assets/stg7enm.png", 12, 193, 47, 62));
        animator.addSpriteForAnim(MOVERIGHT, BufferedImageManager.getImage("assets/stg7enm.png", 74,193,47,61));
        animator.addSpriteForAnim(MOVERIGHT, BufferedImageManager.getImage("assets/stg7enm.png", 136,193,47,61));
        animator.addSpriteForAnim(MOVERIGHT, BufferedImageManager.getImage("assets/stg7enm.png", 205,193,44,61));

        animator.addSpriteForAnim(ATK, BufferedImageManager.getImage("assets/stg7enm.png", 12, 128, 43, 65));
        animator.addSpriteForAnim(ATK, BufferedImageManager.getImage("assets/stg7enm.png", 74,128,46,64));
        animator.addSpriteForAnim(ATK, BufferedImageManager.getImage("assets/stg7enm.png", 138,128,43,66));
        animator.addSpriteForAnim(ATK, BufferedImageManager.getImage("assets/stg7enm.png", 200,128,46,66));
        animator.setCurrentAnim(IDLE);

        lampu = new GameObject();
        lampu.parent = gameObject;
        lampu.renderer = new SpriteRenderer("assets/etama2.png",128,80,128,128);
        lampu.components.add(new DevilKoishiEffectScript(lampu));

    }

    @Override
    public void update() {
        animator.update();
        if(state == null){
            gameObject.destroy();

        }else{
            state.update();
            movement.update();
        }
    }

    @Override
    public void lateUpdate() {

    }

    class KomeijiState{

        public void update(){

        }
    }

    class KomeijiIdleAndShift extends  KomeijiState{
        float idleTime = 1;
        float t = 0;
        KomeijiState shiftTo;


        public void update(){
            idleTime -= GamePanel.deltaTime;
            if(idleTime <= 0)
                state = shiftTo;
        }
    }

    class KomeijiMovement extends  KomeijiState{
        Vec2 dest;
        float speed;
        float lastDist;
        public KomeijiMovement(){

            dest = new Vec2(gameObject.position);
            lastDist = 0;
            speed = 100;
        }

        @Override
        public void update() {
            Vec2 dir = dest.sub(gameObject.position);

            if(!(dir.magsqr()< 10)){ // far dest
                gameObject.position = gameObject.position.add(dir.norm().mul(speed * (float) GamePanel.deltaTime));
                if(lastDist < 10 && dest.x > gameObject.position.x)
                    animator.setCurrentAnim(MOVELEFT);
                else if(lastDist < 10 && dest.x < gameObject.position.x)
                    animator.setCurrentAnim(MOVERIGHT);
            }else if(lastDist > 10){ // near dest
                animator.setCurrentAnim(IDLE);
            }
            lastDist = dir.magsqr();
        }
    }

    class KomeijiFirstPhase extends KomeijiState{
        float timePassed = 0;
        float soundReload = 0;
        float reloadTime = 2f;
        int shootCount = 0;
        int doubleShootCount = 0;
        float radius = 90;
        float logarithmicParam = 0;
        boolean flip = false;

        float baseRotation = 0;
        public void update(){
            soundReload -= GamePanel.deltaTime;
            timePassed += GamePanel.deltaTime;
            //System.out.print(timePassed);
            if(timePassed >= 30.f){
                KomeijiSecondPhase ph= new KomeijiSecondPhase();
                KomeijiIdleAndShift is = new KomeijiIdleAndShift();
                is.shiftTo = ph;
                is.idleTime = 2.f;
                state = is;
                GameObject obj = new GameObject();
                obj.components.add(new BulletCleanerScript(obj));
                AudioManager.play("musuh_mati");
            }else{

                reloadTime -= GamePanel.deltaTime;

                if(reloadTime <= 0) {
                    shootCount++;
                    reloadTime = 0.05f + reloadTime;
                    GameObject b = new GameObject();
                    KoishiFirstPhaseAttackScript sc = new KoishiFirstPhaseAttackScript(b);
                    sc.baseRotation = baseRotation;
                    sc.colorflip = doubleShootCount;
                    b.components.add(sc);
                    b.position.x = (gameObject.position.x - (float)(radius * logarithmicParam * Math.cos(logarithmicParam)));
                    b.position.y = (gameObject.position.y - (float)(radius * logarithmicParam* Math.sin(logarithmicParam)));
                    if(!flip)
                        logarithmicParam += 0.3f;
                    else
                        logarithmicParam -= 0.3f;
                    if(soundReload <= 0) {
                        AudioManager.play("boss_serang00", -20.f);
                        soundReload = 0.1f;
                    }
                    //baseRotation += 0.05f;
                }
                if(shootCount == 15){
                    shootCount = 0;
                    doubleShootCount++;
                    baseRotation = 0;
                    if(doubleShootCount < 2)
                        reloadTime = 3.f;
                    else {
                        reloadTime = 3;
                        doubleShootCount = 0;
                    }

                    logarithmicParam = 0;
                    flip = !flip;
                }
            }
        }
    }

    class KomeijiSecondPhase extends KomeijiState{
        float soundReload = 0;

        final Vec2[] dests= {
            new Vec2(0, -150),
            new Vec2(-30, -180),
                new Vec2(10, -157),
              new Vec2(50, -168)
        };
        float timePassed = 0;
        float planeRotation = 0;
        float reloadTime = 2.f;
        int bulletnumber = 0;
        Random rand = new Random();
        float bbReloadTime = 3.f;
        @Override
        public void update() {

            timePassed += GamePanel.deltaTime;
            reloadTime -= GamePanel.deltaTime;
            soundReload -= GamePanel.deltaTime;
            bbReloadTime -= GamePanel.deltaTime;
            if(timePassed >= 30) {
                KomeijiIdleAndShift a = new KomeijiIdleAndShift();
                movement.dest = new Vec2(0,0);
                a.idleTime = movement.dest.sub(gameObject.position).mag() / (100) + 3.f;
                a.shiftTo = new KomeijiThirdPhase();
                state = a;
                GameObject obj = new GameObject();
                obj.components.add(new BulletCleanerScript(obj));
                AudioManager.play("musuh_mati");
                return;
            }
            if(reloadTime <= 0){
                reloadTime += 0.1f;
                for(int i = 0; i < 12; i++) {
                    GameObject b1 = new GameObject();
                    b1.velocity = new Vec2(-300, 0).rotate(planeRotation);
                    b1.position = new Vec2(gameObject.position);
                    KoishiSecondPhaseAttackScript sc = new KoishiSecondPhaseAttackScript(b1);
                    sc.timeBeforeVelocityShift = 1.f - bulletnumber * 0.1f;
                    sc.timeBeforeStop = bulletnumber * 0.1f;
                    b1.components.add(sc);
                    b1.components.add(new GeneralBulletScript(b1));

                    b1.renderer = new SpriteRenderer("assets/etama.png", 34, 64, 12, 16);
                    b1.rotation = (float) Math.PI / 2 + planeRotation;
                    b1.tag = "enemybl";

                    GameObject b2 = new GameObject();
                    b2.velocity = new Vec2(300, 0).rotate(planeRotation);
                    b2.position = new Vec2(gameObject.position);
                    sc = new KoishiSecondPhaseAttackScript(b2);

                    sc.timeBeforeStop = bulletnumber * 0.1f;
                    sc.timeBeforeVelocityShift = 1f - sc.timeBeforeStop;
                    b2.components.add(sc);
                    b2.components.add(new GeneralBulletScript(b2));
                    b2.renderer = new SpriteRenderer("assets/etama.png", 34, 64, 12, 16);
                    b2.rotation = -(float) Math.PI / 2 + planeRotation;
                    b2.tag = "enemybl";



                    bulletnumber++;
                }

                if(bbReloadTime <= 0){
                    bbReloadTime += 0.5f;
                    GameObject b3 = new GameObject();
                    b3.velocity = new Vec2(300, 0).rotate(planeRotation).getPerpendicular().mul(0.5f);
                    b3.position = new Vec2(gameObject.position);


                    b3.components.add(new GeneralBulletScript(b3));
                    b3.renderer = new SpriteRenderer("assets/etama3.png", 98, 114, 28, 28);
                    b3.tag = "enemybl";
                }
                planeRotation = planeRotation + ((float)(rand.nextInt(3)) + -1.f) * 2.f * (float)GamePanel.deltaTime;
                if(planeRotation > 0)
                    planeRotation = planeRotation >= 0.07f ? 0.07f : planeRotation;
                else
                    planeRotation = planeRotation <= -0.07f ? -0.07f : planeRotation;

                bulletnumber = 1;
                reloadTime = 0.2f;

                if(rand.nextFloat() < 0.1f && animator.getCurrentAnim() == IDLE){
                    movement.dest = dests[rand.nextInt(dests.length)];
                }

                if(soundReload <= 0.f) {
                    AudioManager.play("boss_serang00", -20.f);
                    soundReload = 0.3f;
                }
            }


        }
    }

    class KomeijiThirdPhase extends KomeijiState{
        float timePassed = 0;
        float reloadTime = 0;
        float initialRotation = 0;



        @Override
        public void update() {
            timePassed += GamePanel.deltaTime;
            reloadTime -= GamePanel.deltaTime;

            if(animator.getCurrentAnim() != ATK )
                animator.setCurrentAnim(ATK);

            if(timePassed >= 32){
                GameObject obj = new GameObject();
                obj.components.add(new BulletCleanerScript(obj));
                AudioManager.play("boss_mati", -20);
                gameObject.destroy();
                lampu.destroy();
                return;
            }
            if(reloadTime <= 0){
                reloadTime += 0.4f;
                AudioManager.play("boss_serang00", -20);
                Vec2 basePositionOffset = new Vec2(0, 1);
                for (int i = 1; i <= 12; i++){
                    GameObject g = new GameObject();
                    g.renderer = new SpriteRenderer("assets/etama8.png",96,0,32,32 );
                    g.rotation =   (float)(i * (Math.PI*2) / (12)) - initialRotation;
                    Vec2 rotatedPositionOffset = basePositionOffset.rotate(g.rotation);
                    g.position = gameObject.position.add(rotatedPositionOffset.mul(30));
                    g.velocity = rotatedPositionOffset.mul(80);
                    GeneralBulletScript gg = new GeneralBulletScript(g);
                    gg.radius = 7;
                    g.components.add(gg);

                    HeartAttackScript hs = new HeartAttackScript(g);
                    hs.rotationalSpeed = 0.4f;
                    g.components.add(hs);
                    g.tag = "enemybl";
                }
                for (int i = 0; i < 12; i++){
                    GameObject g = new GameObject();
                    g.renderer = new SpriteRenderer("assets/etama8.png",64,0,32,32 );
                    g.rotation = (float)(i * (Math.PI*2) / (12)) + initialRotation;
                    Vec2 rotatedPositionOffset = basePositionOffset.rotate(g.rotation);
                    g.position = gameObject.position.add(rotatedPositionOffset.mul(30));
                    g.velocity = rotatedPositionOffset.mul(80);
                    GeneralBulletScript gg = new GeneralBulletScript(g);
                    gg.radius = 7;
                    g.components.add(gg);
                    HeartAttackScript hs = new HeartAttackScript(g);
                    hs.rotationalSpeed = -0.4f;
                    g.components.add(hs);
                    g.tag = "enemybl";
                }
                initialRotation += 0.1f;
            }
        }
    }

    class KomeijiForthPhase extends KomeijiState{
        float t = 0;
        float reloadTime = 0;
        @Override
        public void update() {
            t += GamePanel.deltaTime;
            if(reloadTime <= 0){

            }
        }
    }
}
