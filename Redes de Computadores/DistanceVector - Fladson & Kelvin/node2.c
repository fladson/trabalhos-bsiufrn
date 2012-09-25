#include <stdio.h>

#define NODE_ID 2
#define INFINITO 999 // decidi declarar em uma variável pois se caso mude o valor padrão basta mudar aqui

extern struct rtpkt {
  int sourceid;       /* id of sending router sending this pkt */
  int destid;         /* id of router to which pkt being sent 
                         (must be an immediate neighbor) */
  int mincost[4];    /* min cost to node 0 ... 3 */
  };

extern int TRACE;
extern int YES;
extern int NO;

struct distance_table 
{
  int costs[4][4];
} dt2;


/* students to write the following two routines, and maybe some others */
// os pacotes que vou enviar para os nós 0, 1 e 3 com os meus custos e mais o sourceid e destid
struct rtpkt envio;

void rtinit2()
{
  int custos[4] = {3,1,0,2}; //vetor com os meus custos para os nós 0,1,2 e 3 respectivamente
  int i,j;
  for(i=0;i<4;i++){
    envio.sourceid = 2; // todos sairão com o sourceid = 2
    envio.destid = i;
    for(j=0;j<4;j++){
      envio.mincost[j] = custos[j]; // preenchendo os custos dos pacotes a enviar
      if(i==j)
        dt2.costs[i][j] = custos[i]; // se os indices da matriz forem iguais, então atualizo meus custos buscando no vetor de custos
      else
        dt2.costs[i][j] = INFINITO; // senão eu coloco o valor correspondente ao INFINITO
    }
    if(i!=2)
      tolayer2(envio);
  }

  printdt2(&dt2);
}

void rtupdate2(rcvdpkt)
  struct rtpkt *rcvdpkt;
{
  int i, j, mudanca = 0;
  int src_id = rcvdpkt->sourceid;

  struct rtpkt pkt_aux;
  for(i = 0; i < 4; i++)
  { 
    pkt_aux.mincost[i] = INFINITO;
  }
  
  for(i = 0; i < 4; ++i)
  {
    if(dt2.costs[i][src_id] > rcvdpkt->mincost[i]  + dt2.costs[src_id][src_id])
    {
      dt2.costs[i][src_id] = rcvdpkt->mincost[i]  + dt2.costs[src_id][src_id];
      mudanca = 1;
    }
  }
  
  // Se houve alguma mudança no laço acima a variável 'mudanca' foi mudada para '1' então devo mandar a nova tabela para os meus vizinhos
  if(mudanca)
  {
    for(i=0;i<4;i++)
    {
      for(j=0; j<4; j++)
      {
        if(pkt_aux.mincost[i] > dt2.costs[i][j]) 
          pkt_aux.mincost[i] = dt2.costs[i][j];
      }  
    }
    
	pkt_aux.sourceid = NODE_ID;
    pkt_aux.destid = 0;
    tolayer2(pkt_aux);

    pkt_aux.destid = 1;
    tolayer2(pkt_aux);

    pkt_aux.destid = 3;
    tolayer2(pkt_aux);
  }
  printdt2(&dt2);
}

printdt2(dtptr)
  struct distance_table *dtptr;
  
{
  printf("                via     \n");
  printf("   D2 |    0     1    3 \n");
  printf("  ----|-----------------\n");
  printf("     0|  %3d   %3d   %3d\n",dtptr->costs[0][0],
	 dtptr->costs[0][1],dtptr->costs[0][3]);
  printf("dest 1|  %3d   %3d   %3d\n",dtptr->costs[1][0],
	 dtptr->costs[1][1],dtptr->costs[1][3]);
  printf("     3|  %3d   %3d   %3d\n",dtptr->costs[3][0],
	 dtptr->costs[3][1],dtptr->costs[3][3]);
}