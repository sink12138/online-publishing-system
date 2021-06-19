using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ArticleManager
{
    public partial class ReciveComment : Form
    {
        SqlConnection Sc;
        SqlDataAdapter Sda;
        DataSet Ds;
        DataTable Dt;
        int articleid;
        public ReciveComment(int x)
        {
            articleid = x;
            InitializeComponent();
            string Con = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=ArticleManagerDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            SqlConnection Sc = new SqlConnection(Con);
            SqlDataAdapter Sda = new SqlDataAdapter("select Reviewer.reviewerid,Reviewer.name,Reviewer.email from Reviewer,ReviewerRelationship where Reviewer.reviewerid=ReviewerRelationship.reviewerid and ReviewerRelationship.articleid="+ articleid +" and canceled=0 and datesent is null", Sc);
            DataSet Ds = new DataSet();
            Sda.Fill(Ds);
            DataTable Dt = Ds.Tables[0];
            dataGridView1.AutoGenerateColumns = true;
            dataGridView1.DataSource = Dt;
            dataGridView1.AllowUserToAddRows = false;
            dataGridView1.ReadOnly = true;
            dataGridView1.Columns[0].HeaderCell.Value = "ID";
            dataGridView1.Columns[1].HeaderCell.Value = "姓名";
            dataGridView1.Columns[2].HeaderCell.Value = "E-mail";
        }

        private void ReciveComment_Load(object sender, EventArgs e)
        {
            this.FormBorderStyle = FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
        }

        private void label2_Click(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            button1.Enabled = false;
            if (dataGridView1.Rows.Count == 0)
            {
                MessageBox.Show("没有可选的审稿人!");
                button1.Enabled = true;
                this.Close();
                return;
            }
            string comment = textBox1.Text;
            string Con = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=ArticleManagerDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            Sc = new SqlConnection(Con);
            Sda = new SqlDataAdapter("select * from ReviewerRelationship", Sc);
            Ds = new DataSet();
            Sda.Fill(Ds);
            Dt = Ds.Tables[0];
            SqlCommand scmd = new SqlCommand("update ReviewerRelationship set datesent=getdate(),review=@review where articleid=@articleid and reviewerid=@reviewerid and datesent is null", Sc);
            scmd.Parameters.Add(new SqlParameter("@articleid", SqlDbType.Int, 4, "articleid"));
            scmd.Parameters.Add(new SqlParameter("@reviewerid", SqlDbType.Int, 4, "reviewerid"));
            scmd.Parameters.Add(new SqlParameter("@review", SqlDbType.Text, 999999, "review"));
            Sda.UpdateCommand = scmd;
            foreach(DataRow dr in Dt.Rows)
            {
                if (articleid.ToString()==dr["articleid"].ToString() && dataGridView1.CurrentRow.Cells[0].Value.ToString() == dr["reviewerid"].ToString())
                {
                    try
                    {
                        dr["articleid"] = articleid;
                        dr["reviewerid"] = dataGridView1.CurrentRow.Cells[0].Value;
                        dr["review"] = comment;
                        Sda.Update(Ds);
                    }
                    catch { };
                }
            }



            MessageBox.Show("评论接收成功！");
            button1.Enabled = true;
            this.Close();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
