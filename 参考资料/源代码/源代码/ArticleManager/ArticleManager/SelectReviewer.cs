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
    public partial class SelectReviewer : Form
    {
        SqlConnection Sc;
        SqlDataAdapter Sda;
        DataSet Ds;
        DataTable Dt;
        public string name;
        public bool operationSucceed = false;
        public int reviewerid;
        public int articleid;

        public SelectReviewer(int x)
        {
            articleid = x;
            InitializeComponent();
            string Con = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=ArticleManagerDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            Sc = new SqlConnection(Con);
            Sda = new SqlDataAdapter("select * from Reviewer", Sc);
            Ds = new DataSet();
            Sda.Fill(Ds);
            Dt = Ds.Tables[0];
            dataGridView1.AutoGenerateColumns = true;
            dataGridView1.DataSource = Dt;
            dataGridView1.AllowUserToAddRows = false;
            dataGridView1.ReadOnly = true;
            dataGridView1.Columns[0].HeaderCell.Value = "ID";
            dataGridView1.Columns[1].HeaderCell.Value = "姓名";
            dataGridView1.Columns[2].HeaderCell.Value = "E-mail";
        }

        bool checkit(int articleid,int reviewerid)
        {
            SqlDataAdapter Sda = new SqlDataAdapter("select * from ReviewerRelationship where canceled=0 and datesent is null", Sc);
            DataSet Ds = new DataSet();
            Sda.Fill(Ds);
            DataTable Dt = Ds.Tables[0];
            for (int i = 0; i < Dt.Rows.Count; i++)
            {
                if (Convert.ToInt32(Dt.Rows[i]["articleid"]) == articleid && Convert.ToInt32(Dt.Rows[i]["reviewerid"]) == reviewerid)
                    return true;
            }
            return false;
        }

        private void UpdateAuthor_button_Click(object sender, EventArgs e)
        {
            operationSucceed = true;
            reviewerid = Convert.ToInt32(dataGridView1.CurrentRow.Cells[0].Value);
            name = dataGridView1.CurrentRow.Cells[1].Value.ToString();
            if (checkit(articleid, reviewerid))
            {
                MessageBox.Show("已添加当前审稿人");
                return;
            }
            UpdateAuthor_button.Enabled = false;
            string Con = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=ArticleManagerDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            SqlConnection Sc = new SqlConnection(Con);
            SqlDataAdapter Sda = new SqlDataAdapter("select * from ReviewerRelationship", Sc);
            DataSet Ds = new DataSet();
            Sda.Fill(Ds);
            DataTable Dt = Ds.Tables[0];
            SqlCommand scmd = new SqlCommand("insert into ReviewerRelationship(articleid,reviewerid,createtime,canceled) values(@articleid,@reviewerid,getdate(),0)", Sc);
            scmd.Parameters.Add(new SqlParameter("@articleid", SqlDbType.Int, 4, "articleid"));
            scmd.Parameters.Add(new SqlParameter("@reviewerid", SqlDbType.Int, 4, "reviewerid"));
            Sda.InsertCommand = scmd;
            DataRow dr = Dt.NewRow();
            try
            {
                dr["articleid"] = articleid;
                dr["reviewerid"] = reviewerid;
                Dt.Rows.Add(dr);
                Sda.Update(Ds);
            }
            catch { };
            MessageBox.Show("分配审稿人成功！");
            this.Close();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            operationSucceed = false;
            this.Close();
        }

        private void SelectReviewer_Load(object sender, EventArgs e)
        {
            this.FormBorderStyle = FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
        }
    }
}
