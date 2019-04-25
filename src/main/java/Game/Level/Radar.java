package Game.Level;

import Game.*;
import Game.Effect.VisualEffect.EffectController;
import Game.Effect.VisualEffect.ExplosionEffect;
import Game.MyExeption.CountExeption;
import Game.MyExeption.NotIdExeption;
import Io.Input;
import graphics.TextureAtlas;

import java.awt.*;
import java.util.ArrayList;


public class Radar extends ArrayList
{
  private ArrayList<Igrok> plaeyers;
  private ContactSide contact, contactSnaryad;
  private EffectController effectController;
  private TextureAtlas atlas;


  public Radar(ArrayList<Igrok> plaeyers, float scale)
  {
    this.plaeyers = plaeyers;
    contact = new ContactSide(scale);
    contactSnaryad = new ContactSide(scale);
    contactSnaryad.setBoxAtackScale(GameStart.TILE_SCALE_SIZE);
    effectController = new EffectController();
  }

  public Radar(float scales, TextureAtlas atlas)
  {
    float scale = scales;
    plaeyers = new ArrayList<Igrok>();
    contact = new ContactSide(scale);
    contactSnaryad = new ContactSide(scale);
    contactSnaryad.setBoxAtackScale(scale / 2);
    effectController = new EffectController();
    this.atlas = atlas;

  }

  @Override
  public int size()
  {
    return plaeyers.size();
  }

  public void update(Input input)
  {
    for (int i = 0; i < plaeyers.size(); i++)
    {
      plaeyers.get(i).update(input);
    }
    effectController.update();
  }

  public void render(Graphics2D g)
  {
    for (int i = 0; i < plaeyers.size(); i++)
    {
      plaeyers.get(i).render(g);
    }
    effectController.render(g);
  }

  public void addEntity(Igrok igrok)
  {
    plaeyers.add(igrok);
  }

  public void detonation_of_bomb(int id)
  {
    ArrayList<Integer> idList = new ArrayList<Integer>();
    if (!isBotType(id))
    {
      for (int i = 0; i < size(); i++)
      {
        if (plaeyers.get(i).type == EntityType.Bot)
        {
          idList.add(plaeyers.get(i).id);
        }
      }
    }
    else
    {
      for (int i = 0; i < size(); i++)
      {
        if (plaeyers.get(i).type == EntityType.Player)
        {
          idList.add(plaeyers.get(i).id);
          break;
        }
      }
    }
    for (int i = 0; i < idList.size(); i++)
    {
      try
      {
        remuveById(idList.get(i));
      }
      catch (NotIdExeption notIdExeption)
      {
        notIdExeption.printStackTrace();
      }
      catch (CountExeption countExeption)
      {
        countExeption.printStackTrace();
      }
    }
  }

  public void chaengeStatusPauseAllPlayers(int id)
  {

    if (!isBotType(id))
    {
      for (int i = 0; i < size(); i++)
      {
        if (plaeyers.get(i).type == EntityType.Bot)
        {
          plaeyers.get(i).getTank().getLevelTank().chaengeStatusPause();
        }
      }
    }
    else
    {
      for (int i = 0; i < size(); i++)
      {
        if (plaeyers.get(i).type == EntityType.Player)
        {
          plaeyers.get(i).getTank().getLevelTank().chaengeStatusPause();
          break;
        }
      }
    }
  }

  public boolean isContactWithEntity(Point player, int id)
  {
    boolean rezult = false;
    for (int i = 0; i < plaeyers.size(); i++)
    {
      if (plaeyers.get(i).id != id)
      {
        contact.setLocation(player, plaeyers.get(i).getTank().getPositionTank());
      }
      if (contact.atackObstacle())
      {
        rezult = true;
      }
    }
    return rezult;
  }

  public boolean isContactWithSnaryad(Point snaryad, int idShooters)
  {
    boolean rezult = false;
    for (int i = 0; i < size(); i++)
    {
      if (idShooters != plaeyers.get(i).id)
      {
        contactSnaryad.setLocation(snaryad, plaeyers.get(i).getTank().getPositionTank());
        if (contactSnaryad.atackObstacle())
        {
          if (isBotType(idShooters) && plaeyers.get(i).type == EntityType.Player)
          {
            changes_player_level(plaeyers.get(i));
          }
          if (!isBotType(idShooters) && plaeyers.get(i).type == EntityType.Bot)
          {
            changes_player_level(plaeyers.get(i));
          }
          rezult = true;
          break;
        }
      }
    }
    return rezult;
  }

  private void changes_player_level(Igrok igrok)
  {
    if (igrok.getTank().getLevelTank().isStatusBronya())
    {
      return;
    }
    System.out.println("Radar: level " + igrok.getTank().getLevelTank().getLeveltank() +
        " type " + igrok.getTank().getLevelTank().getTypetank());
    if (igrok.getTank().getLevelTank().getLeveltank() == 0)
    {
      killPlayer(igrok);
    }
    else
    {
      igrok.getTank().getLevelTank().DownLevel_Tanks();
    }
    System.out.println("Radar: level " + igrok.getTank().getLevelTank().getLeveltank() +
        " type " + igrok.getTank().getLevelTank().getTypetank());
  }

  private void killPlayer(Igrok igrok)
  {
    if (igrok.getTank().getLevelTank().getLife() == 0)
    {
      try
      {
        addEffect(igrok.getTank().getPositionTank());
        remuveById(igrok.id);
      }
      catch (NotIdExeption notIdExeption)
      {
        notIdExeption.printStackTrace();
      }
      catch (CountExeption countExeption)
      {
        countExeption.printStackTrace();
      }
    }
    else
    {
      igrok.getTank().getLevelTank().downLife();
      igrok.getTank().setLocation(GameRun.x, GameRun.y);
      igrok.getTank().bronyaOn();
    }
  }

  public Point getPointPlayer()
  {
    for (int i = 0; i < size(); i++)
    {
      if (plaeyers.get(i).type == EntityType.Player)
      {
        return plaeyers.get(i).getTank().getPositionTank();
      }
    }
    return null;
  }

  private boolean isBotType(int id)
  {
    for (int i = 0; i < size(); i++)
    {
      if (plaeyers.get(i).id == id && plaeyers.get(i).type == EntityType.Bot)
      {
        return true;
      }
    }
    return false;
  }

  private void addEffect(Point point)
  {
    effectController.addEffect(new ExplosionEffect(point.x, point.y, GameStart.GAME_SCALE, atlas));
  }

  public boolean remuveById(int id) throws NotIdExeption, CountExeption
  {
    //System.out.println(id);

    boolean rezult = false;
    testId(id);
    int iterator = -1;
    for (int i = 0; i < plaeyers.size(); i++)
    {
      if (plaeyers.get(i).id == id)
      {
        iterator = i;
      }
    }
    if (iterator > -1)
    {
      plaeyers.remove(iterator);
      rezult = true;
    }
    System.out.println("(Radar) удалён ли танк с id " + id + " " + rezult);
    return rezult;
  }

  private void testId(int id) throws NotIdExeption, CountExeption
  {
    int counеСoincidence = 0;
    for (int i = 0; i < plaeyers.size(); i++)
    {
      if (plaeyers.get(i).id == id)
      {
        counеСoincidence++;
      }
    }
    if (counеСoincidence == 0)
    {
      throw new NotIdExeption("нет объектов с таким id " + id + " ", id);
    }
    else if (counеСoincidence > 1) throw new CountExeption("количество объектов с этим id больше одного", id);


  }
}
